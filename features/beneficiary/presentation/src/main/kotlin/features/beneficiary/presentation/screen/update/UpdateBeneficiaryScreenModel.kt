package features.beneficiary.presentation.screen.update

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import foundation.watchers.RefreshBeneficiaryPublisher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class UpdateBeneficiaryScreenModel(
    private val beneficiaryRepository: BeneficiaryRepository,
    private val dispatcher: CoroutineDispatcher,
    private val refreshBeneficiaryPublisher: RefreshBeneficiaryPublisher
) : ScreenModel, EventAware<UpdateBeneficiaryEvent> by EventPublisher() {

    private val _state = MutableStateFlow(UpdateBeneficiaryState())
    val state: StateFlow<UpdateBeneficiaryState> = _state

    fun initState(beneficiaryId: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                beneficiaryRepository.getBeneficiaryDetails(beneficiaryId)
            }.handle(
                onStart = {
                    _state.update {
                        it.copy(mode = UpdateBeneficiaryMode.Loading)
                    }
                },
                onFailure = {
                    _state.update {
                        it.copy(mode = UpdateBeneficiaryMode.Error)
                    }
                },
                onSuccess = { response ->
                    _state.update { oldState ->
                        oldState.copy(
                            mode = UpdateBeneficiaryMode.Content,
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
                beneficiaryRepository.updateBeneficiary(
                    id = beneficiaryId,
                    name = state.value.name,
                    bankName = state.value.bankName,
                    bankAddress = state.value.bankAddress,
                    swift = state.value.swift,
                    iban = state.value.iban
                )
            }.handle(
                onSuccess = {
                    refreshBeneficiaryPublisher.publish(Unit)
                    publish(UpdateBeneficiaryEvent.Success)
                },
                onFailure = {
                    publish(UpdateBeneficiaryEvent.Error(it.message.orEmpty()))
                },
                onStart = {
                    _state.update {
                        it.copy(mode = UpdateBeneficiaryMode.Loading)
                    }
                },
                onFinish = {
                    _state.update {
                        it.copy(mode = UpdateBeneficiaryMode.Content)
                    }
                }
            )
        }
    }
}