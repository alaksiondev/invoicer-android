package features.beneficiary.presentation.screen.details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.beneficiary.domain.repository.BeneficiaryRepository
import foundation.date.impl.defaultFormat
import foundation.exception.RequestError
import foundation.network.request.handle
import foundation.network.request.launchRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class BeneficiaryDetailsScreenModel(
    private val beneficiaryRepository: BeneficiaryRepository,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel {

    private val _state = MutableStateFlow(BeneficiaryDetailsState())
    val state: StateFlow<BeneficiaryDetailsState> = _state

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

    private fun getErrorType(error: RequestError): BeneficiaryErrorType {
        return when (error) {
            is RequestError.Http -> {
                when (error.httpCode) {
                    404, 403 -> BeneficiaryErrorType.NotFound
                    else -> BeneficiaryErrorType.Generic
                }
            }

            else -> BeneficiaryErrorType.Generic
        }
    }

}