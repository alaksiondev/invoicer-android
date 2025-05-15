package io.github.alaksion.invoicer.features.intermediary.presentation.screen.update

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.network.request.handle
import foundation.network.request.launchRequest
import io.github.alaksion.invoicer.foundation.watchers.RefreshIntermediaryPublisher
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class UpdateIntermediaryScreenModel(
    private val intermediaryRepository: IntermediaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntermediaryPublisher: RefreshIntermediaryPublisher
) : ScreenModel {

    private val _state = MutableStateFlow(UpdateIntermediaryState())
    val state: StateFlow<UpdateIntermediaryState> = _state

    private val _events = MutableSharedFlow<UpdateIntermediaryEvent>()
    val events = _events.asSharedFlow()

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
                    _events.emit(UpdateIntermediaryEvent.Success)
                },
                onFailure = {
                    _events.emit(UpdateIntermediaryEvent.Error(it.message.orEmpty()))
                },
                onStart = {
                    _state.update {
                        it.copy(isButtonLoading = true)
                    }
                },
                onFinish = {
                    _state.update {
                        it.copy(isButtonLoading = false)
                    }
                }
            )
        }
    }
}
