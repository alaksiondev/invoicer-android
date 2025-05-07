package io.github.alaksion.invoicer.features.intermediary.presentation.screen.details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.network.RequestError
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.watchers.RefreshIntermediaryPublisher
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository
import io.github.alaksion.invoicer.foundation.utils.date.defaultFormat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class IntermediaryDetailsScreenModel(
    private val intermediaryRepository: IntermediaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntermediaryPublisher: RefreshIntermediaryPublisher
) : ScreenModel {

    private val _state = MutableStateFlow(IntermediaryDetailsState())
    val state: StateFlow<IntermediaryDetailsState> = _state

    private val _events = MutableSharedFlow<IntermediaryDetailsEvent>()
    val events = _events.asSharedFlow()

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

    fun deleteIntermediary(id: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                intermediaryRepository.deleteIntermediary(id)
            }.handle(
                onFailure = { error ->
                    _events.emit(IntermediaryDetailsEvent.DeleteError(error.message ?: ""))
                },
                onSuccess = {
                    refreshIntermediaryPublisher.publish(Unit)
                    _events.emit(IntermediaryDetailsEvent.DeleteSuccess)
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