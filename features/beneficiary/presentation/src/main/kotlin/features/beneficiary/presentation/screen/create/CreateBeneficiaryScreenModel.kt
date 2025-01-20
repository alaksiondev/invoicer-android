package features.beneficiary.presentation.screen.create

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.beneficiary.domain.repository.BeneficiaryRepository
import features.beneficiary.publisher.NewBeneficiaryPublisher
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

internal class CreateBeneficiaryScreenModel(
    private val beneficiaryRepository: BeneficiaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val newBeneficiaryPublisher: NewBeneficiaryPublisher,
) : ScreenModel, EventAware<CreateBeneficiaryEvents> by EventPublisher() {

    private val _state = MutableStateFlow(CreateBeneficiaryState())
    val state: StateFlow<CreateBeneficiaryState> = _state

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
                beneficiaryRepository.createIntermediary(
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
                        CreateBeneficiaryEvents.Error(message = it.message.orEmpty())
                    )
                },
                onSuccess = {
                    newBeneficiaryPublisher.publish(Unit)
                    publish(CreateBeneficiaryEvents.Success)
                }
            )
        }
    }

    fun cancelSubmit() {
        submitJob?.cancel()
        submitJob = null
    }
}