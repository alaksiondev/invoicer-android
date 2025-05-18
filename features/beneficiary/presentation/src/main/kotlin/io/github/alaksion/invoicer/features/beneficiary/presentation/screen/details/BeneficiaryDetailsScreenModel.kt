package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.foundation.network.RequestError
import io.github.alaksion.invoicer.foundation.network.request.handle
import io.github.alaksion.invoicer.foundation.network.request.launchRequest
import io.github.alaksion.invoicer.foundation.watchers.RefreshBeneficiaryPublisher
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
import io.github.alaksion.invoicer.foundation.utils.date.defaultFormat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val NotFound = 404
private const val Forbidden = 403

internal class BeneficiaryDetailsScreenModel(
    private val beneficiaryRepository: BeneficiaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val refreshBeneficiaryPublisher: RefreshBeneficiaryPublisher
) : ScreenModel {

    private val _state = MutableStateFlow(BeneficiaryDetailsState())
    val state: StateFlow<BeneficiaryDetailsState> = _state

    private val _events = MutableSharedFlow<BeneficiaryDetailsEvent>()
    val events = _events.asSharedFlow()

    fun initState(id: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                beneficiaryRepository.getBeneficiaryDetails(id)
            }.handle(
                onFailure = { error ->
                    _state.update {
                        it.copy(mode = BeneficiaryDetailsMode.Error(type = getErrorType(error)))
                    }
                },
                onSuccess = { response ->
                    _state.update {
                        it.copy(
                            name = response.name,
                            iban = response.iban,
                            swift = response.swift,
                            bankName = response.bankName,
                            bankAddress = response.bankAddress,
                            createdAt = response.createdAt.defaultFormat(),
                            updatedAt = response.updatedAt.defaultFormat(),
                            mode = BeneficiaryDetailsMode.Content
                        )
                    }
                },
                onStart = {
                    _state.update {
                        it.copy(mode = BeneficiaryDetailsMode.Loading)
                    }
                }
            )
        }
    }

    fun deleteBeneficiary(id: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                beneficiaryRepository.deleteBeneficiary(id)
            }.handle(
                onFailure = { error ->
                    _events.emit(BeneficiaryDetailsEvent.DeleteError(error.message ?: ""))
                },
                onSuccess = {
                    refreshBeneficiaryPublisher.publish(Unit)
                    _events.emit((BeneficiaryDetailsEvent.DeleteSuccess))
                },
                onStart = {
                    _state.update {
                        it.copy(mode = BeneficiaryDetailsMode.Loading)
                    }
                },
                onFinish = {
                    _state.update {
                        it.copy(mode = BeneficiaryDetailsMode.Content)
                    }
                }
            )
        }
    }

    private fun getErrorType(error: RequestError): BeneficiaryErrorType {
        return when (error) {
            is RequestError.Http -> {
                when (error.httpCode) {
                    NotFound, Forbidden -> BeneficiaryErrorType.NotFound
                    else -> BeneficiaryErrorType.Generic
                }
            }

            else -> BeneficiaryErrorType.Generic
        }
    }

}
