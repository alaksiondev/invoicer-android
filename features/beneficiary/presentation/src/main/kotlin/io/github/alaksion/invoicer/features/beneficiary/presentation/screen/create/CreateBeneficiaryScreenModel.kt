package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.watchers.RefreshBeneficiaryPublisher
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CreateBeneficiaryScreenModel(
    private val beneficiaryRepository: BeneficiaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val refreshBeneficiaryPublisher: RefreshBeneficiaryPublisher,
) : ScreenModel {

    private val _state = MutableStateFlow(CreateBeneficiaryState())
    val state: StateFlow<CreateBeneficiaryState> = _state

    private val _events = MutableSharedFlow<CreateBeneficiaryEvents>(
        replay = 1
    )
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
                beneficiaryRepository.createBeneficiary(
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
                        CreateBeneficiaryEvents.Error(message = it.message.orEmpty())
                    )
                },
                onSuccess = {
                    refreshBeneficiaryPublisher.publish(Unit)
                    _events.emit((CreateBeneficiaryEvents.Success))
                }
            )
        }
    }
}
