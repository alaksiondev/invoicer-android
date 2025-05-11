package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.network.request.handle
import foundation.network.request.launchRequest
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class PickBeneficiaryScreenModel(
    private val createInvoiceManager: CreateInvoiceManager,
    private val beneficiaryRepository: BeneficiaryRepository,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel {

    private var isInitialized = false

    private val _state = MutableStateFlow(PickBeneficiaryState())
    val state: StateFlow<PickBeneficiaryState> = _state

    private val _events = MutableSharedFlow<PickBeneficiaryEvents>()
    val events = _events.asSharedFlow()

    fun initState(
        force: Boolean = false
    ) {
        if (isInitialized.not() || force) {
            screenModelScope.launch(dispatcher) {
                // No scrolling on this page yet
                launchRequest {
                    beneficiaryRepository.getBeneficiaries(
                        page = 0,
                        limit = 100
                    )
                }.handle(
                    onStart = {
                        _state.update {
                            it.copy(
                                uiMode = PickBeneficiaryUiMode.Loading
                            )
                        }
                    },
                    onFinish = { /* no op*/ },
                    onSuccess = { data ->
                        _state.update {
                            it.copy(
                                uiMode = PickBeneficiaryUiMode.Content,
                                beneficiaries = data.items.toPersistentList(),
                                selection = BeneficiarySelection.Existing(
                                    id = createInvoiceManager.beneficiaryId
                                )
                            )
                        }
                    },
                    onFailure = {
                        _state.update {
                            it.copy(
                                uiMode = PickBeneficiaryUiMode.Error
                            )
                        }
                    }
                )
            }
            isInitialized = true
        }
    }

    fun updateSelection(selection: BeneficiarySelection) {
        when (selection) {
            BeneficiarySelection.None, BeneficiarySelection.New ->
                _state.update { it.copy(selection = selection) }

            is BeneficiarySelection.Existing -> {
                val item = _state.value.beneficiaries.firstOrNull { it.id == selection.id }
                item?.let { beneficiary ->
                    _state.update {
                        it.copy(
                            selection = BeneficiarySelection.Existing(beneficiary.id)
                        )
                    }
                }
            }
        }
    }

    fun submit() {
        if (isInitialized.not()) return

        when (val selection = _state.value.selection) {
            is BeneficiarySelection.Existing -> screenModelScope.launch(dispatcher) {
                val beneficiary = _state.value.beneficiaries.firstOrNull { selection.id == it.id }
                beneficiary?.let {
                    submitBeneficiary(
                        beneficiaryId = beneficiary.id,
                        beneficiaryName = beneficiary.name
                    )
                }

            }

            BeneficiarySelection.New -> screenModelScope.launch(dispatcher) { submitNewBeneficiary() }
            BeneficiarySelection.None -> Unit
        }
    }

    private suspend fun submitNewBeneficiary() {
        _events.emit(PickBeneficiaryEvents.StartNewBeneficiary)
    }

    private suspend fun submitBeneficiary(
        beneficiaryId: String,
        beneficiaryName: String
    ) {
        createInvoiceManager.beneficiaryId = beneficiaryId
        createInvoiceManager.beneficiaryName = beneficiaryName
        _events.emit(PickBeneficiaryEvents.Continue)
    }
}
