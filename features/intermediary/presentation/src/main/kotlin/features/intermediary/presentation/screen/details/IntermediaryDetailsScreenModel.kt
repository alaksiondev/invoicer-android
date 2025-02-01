package features.intermediary.presentation.screen.details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.intermediary.domain.repository.IntermediaryRepository
import features.intermediary.publisher.RefreshIntermediaryPublisher
import foundation.date.impl.defaultFormat
import foundation.events.EventAware
import foundation.events.EventPublisher
import foundation.exception.RequestError
import foundation.network.request.handle
import foundation.network.request.launchRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class IntermediaryDetailsScreenModel(
    private val intermediaryRepository: IntermediaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntermediaryPublisher: RefreshIntermediaryPublisher
) : ScreenModel, EventAware<IntermediaryDetailsEvent> by EventPublisher() {

    private val _state = MutableStateFlow(IntermediaryDetailsState())
    val state: StateFlow<IntermediaryDetailsState> = _state

    fun initState(id: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                intermediaryRepository.getIntermediaryDetails(id)
            }.handle(
                onFailure = { error ->
                    _state.update {
                        it.copy(mode = IntermediaryDetailsMode.Error(type = getErrorType(error)))
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
                            mode = IntermediaryDetailsMode.Content
                        )
                    }
                },
                onStart = {
                    _state.update {
                        it.copy(mode = IntermediaryDetailsMode.Loading)
                    }
                }
            )
        }
    }

    fun deleteBeneficiary(id: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                intermediaryRepository.deleteBeneficiary(id)
            }.handle(
                onFailure = { error ->
                    publish(IntermediaryDetailsEvent.DeleteError(error.message ?: ""))
                },
                onSuccess = {
                    refreshIntermediaryPublisher.publish(Unit)
                    publish(IntermediaryDetailsEvent.DeleteSuccess)
                },
                onStart = {
                    _state.update {
                        it.copy(mode = IntermediaryDetailsMode.Loading)
                    }
                },
                onFinish = {
                    _state.update {
                        it.copy(mode = IntermediaryDetailsMode.Content)
                    }
                }
            )
        }
    }

    private fun getErrorType(error: RequestError): IntermediaryErrorType {
        return when (error) {
            is RequestError.Http -> {
                when (error.httpCode) {
                    404, 403 -> IntermediaryErrorType.NotFound
                    else -> IntermediaryErrorType.Generic
                }
            }

            else -> IntermediaryErrorType.Generic
        }
    }

}