package io.github.alaksion.invoicer.features.intermediary.presentation.screen.create

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.network.request.handle
import foundation.network.request.launchRequest
import io.github.alaksion.invoicer.foundation.watchers.RefreshIntermediaryPublisher
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CreateIntermediaryScreenModel(
    private val intermediaryRepository: IntermediaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntermediaryPublisher: RefreshIntermediaryPublisher,
) : ScreenModel {

    private val _state = MutableStateFlow(CreateIntermediaryState())
    val state: StateFlow<CreateIntermediaryState> = _state

    private val _events = MutableSharedFlow<CreateIntermediaryEvents>()
    val events = _events.asSharedFlow()

    private var submitJob: Job? = null

    fun updateName(value: String) {
        _state.update { oldState ->
            oldState.copy(name = value)
        }
    }

    fun updateSwift(value: String) {
        _state.update { oldState ->
            oldState.copy(swift = value)
        }
    }

    fun updateIban(value: String) {
        _state.update { oldState ->
            oldState.copy(iban = value)
        }
    }

    fun updateBankAddress(value: String) {
        _state.update { oldState ->
            oldState.copy(bankAddress = value)
        }
    }

    fun updateBankName(value: String) {
        _state.update { oldState ->
            oldState.copy(bankName = value)
        }
    }

    fun submit() {
        submitJob = screenModelScope.launch(dispatcher) {
            launchRequest {
                intermediaryRepository.createIntermediary(
                    name = state.value.name,
                    bankAddress = state.value.bankAddress,
                    bankName = state.value.bankName,
                    swift = state.value.swift,
                    iban = state.value.iban
                )
            }.handle(
                onStart = {
                    _state.update {
                        it.copy(isSubmitting = true)
                    }
                },
                onFinish = {
                    _state.update {
                        it.copy(isSubmitting = false)
                    }
                },
                onFailure = {
                    _events.emit(
                        CreateIntermediaryEvents.Error(message = it.message.orEmpty())
                    )
                },
                onSuccess = {
                    refreshIntermediaryPublisher.publish(Unit)
                    _events.emit(CreateIntermediaryEvents.Success)
                }
            )
        }
    }

    fun cancelSubmit() {
        submitJob?.cancel()
        submitJob = null
    }
}
