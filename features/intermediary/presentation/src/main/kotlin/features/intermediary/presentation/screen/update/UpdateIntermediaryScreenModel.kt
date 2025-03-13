package features.intermediary.presentation.screen.update

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.intermediary.domain.repository.IntermediaryRepository
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import foundation.watchers.RefreshIntermediaryPublisher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class UpdateIntermediaryScreenModel(
    private val intermediaryRepository: IntermediaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntermediaryPublisher: RefreshIntermediaryPublisher
) : ScreenModel, EventAware<UpdateIntermediaryEvent> by EventPublisher() {

    private val _state = MutableStateFlow(UpdateIntermediaryState())
    val state: StateFlow<UpdateIntermediaryState> = _state

    fun initState(beneficiaryId: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                intermediaryRepository.getIntermediaryDetails(beneficiaryId)
            }.handle(
                onStart = {
                    _state.update {
                        it.copy(mode = UpdateIntermediaryMode.Loading)
                    }
                },
                onFailure = {
                    _state.update {
                        it.copy(mode = UpdateIntermediaryMode.Error)
                    }
                },
                onSuccess = { response ->
                    _state.update { oldState ->
                        oldState.copy(
                            mode = UpdateIntermediaryMode.Content,
                            name = response.name,
                            bankName = response.bankName,
                            bankAddress = response.bankAddress,
                            swift = response.swift,
                            iban = response.iban
                        )
                    }
                }
            )
        }
    }

    fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun updateBankName(bankName: String) {
        _state.update { it.copy(bankName = bankName) }
    }

    fun updateBankAddress(bankAddress: String) {
        _state.update { it.copy(bankAddress = bankAddress) }
    }

    fun updateSwift(swift: String) {
        _state.update { it.copy(swift = swift) }
    }

    fun updateIban(iban: String) {
        _state.update { it.copy(iban = iban) }
    }

    fun submit(beneficiaryId: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                intermediaryRepository.updateIntermediary(
                    id = beneficiaryId,
                    name = state.value.name,
                    bankName = state.value.bankName,
                    bankAddress = state.value.bankAddress,
                    swift = state.value.swift,
                    iban = state.value.iban
                )
            }.handle(
                onSuccess = {
                    refreshIntermediaryPublisher.publish(Unit)
                    publish(UpdateIntermediaryEvent.Success)
                },
                onFailure = {
                    publish(UpdateIntermediaryEvent.Error(it.message.orEmpty()))
                },
                onStart = {
                    _state.update {
                        it.copy(mode = UpdateIntermediaryMode.Loading)
                    }
                },
                onFinish = {
                    _state.update {
                        it.copy(mode = UpdateIntermediaryMode.Content)
                    }
                }
            )
        }
    }
}