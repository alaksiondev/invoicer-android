package features.intermediary.presentation.screen.create

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.intermediary.domain.repository.IntermediaryRepository
import features.intermediary.publisher.NewIntermediaryPublisher
import foundation.events.EventAware
import foundation.events.EventPublisher
import foundation.network.request.handle
import foundation.network.request.launchRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CreateIntermediaryScreenModel(
    private val intermediaryRepository: IntermediaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val newIntermediaryPublisher: NewIntermediaryPublisher,
) : ScreenModel, EventAware<CreateIntermediaryEvents> by EventPublisher() {

    private val _state = MutableStateFlow(CreateIntermediaryState())
    val state: StateFlow<CreateIntermediaryState> = _state

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
                    iban = state.value.swift
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
                    publish(
                        CreateIntermediaryEvents.Error(message = it.message.orEmpty())
                    )
                },
                onSuccess = {
                    newIntermediaryPublisher.publish(Unit)
                    publish(CreateIntermediaryEvents.Success)
                }
            )
        }
    }

    fun cancelSubmit() {
        submitJob?.cancel()
        submitJob = null
    }
}