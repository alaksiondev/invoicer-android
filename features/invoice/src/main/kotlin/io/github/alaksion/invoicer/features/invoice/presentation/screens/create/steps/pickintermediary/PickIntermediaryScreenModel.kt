package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.foundation.network.request.handle
import io.github.alaksion.invoicer.foundation.network.request.launchRequest
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class PickIntermediaryScreenModel(
    private val createInvoiceManager: CreateInvoiceManager,
    private val intermediaryRepository: IntermediaryRepository,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel {

    private var isInitialized = false

    private val _state = MutableStateFlow(PickIntermediaryState())
    val state: StateFlow<PickIntermediaryState> = _state

    private val _events = MutableSharedFlow<PickIntermediaryEvents>()
    val events = _events.asSharedFlow()

    fun initState(
        force: Boolean = false
    ) {
        if (isInitialized.not() || force) {
            screenModelScope.launch(dispatcher) {
                // No scrolling on this page yet
                launchRequest {
                    // No pagination for this feature yet
                    intermediaryRepository.getIntermediaries(
                        page = 0,
                        limit = 1000
                    )
                }.handle(
                    onStart = {
                        _state.update {
                            it.copy(
                                uiMode = PickIntermediaryUiMode.Loading
                            )
                        }
                    },
                    onFinish = { /* no op*/ },
                    onSuccess = { data ->
                        _state.update {
                            it.copy(
                                uiMode = PickIntermediaryUiMode.Content,
                                intermediaries = data.toPersistentList(),
                                selection = createInvoiceManager
                                    .intermediaryId?.let { selectedIntermediaryId ->
                                        IntermediarySelection.Existing(selectedIntermediaryId)
                                    } ?: IntermediarySelection.None
                            )
                        }
                    },
                    onFailure = {
                        _state.update {
                            it.copy(
                                uiMode = PickIntermediaryUiMode.Error
                            )
                        }
                    }
                )
            }
            isInitialized = true
        }
    }

    fun updateSelection(selection: IntermediarySelection) {
        when (selection) {
            IntermediarySelection.None, IntermediarySelection.New ->
                _state.update { it.copy(selection = selection) }

            is IntermediarySelection.Existing -> {
                val item = _state.value.intermediaries.firstOrNull { it.id == selection.id }
                item?.let { beneficiary ->
                    _state.update {
                        it.copy(
                            selection = IntermediarySelection.Existing(beneficiary.id)
                        )
                    }
                }
            }

            IntermediarySelection.Ignore -> _state.update { it.copy(selection = selection) }
        }
    }

    fun submit() {
        if (isInitialized.not()) return

        when (val selection = _state.value.selection) {
            is IntermediarySelection.Existing -> screenModelScope.launch(dispatcher) {
                val beneficiary = _state.value.intermediaries.firstOrNull { selection.id == it.id }
                beneficiary?.let {
                    submitBeneficiary(
                        intermediaryId = beneficiary.id,
                        intermediaryName = beneficiary.name
                    )
                }

            }

            IntermediarySelection.New -> screenModelScope.launch(dispatcher) { submitNewBeneficiary() }

            IntermediarySelection.Ignore -> screenModelScope.launch(dispatcher) { submitIgnoringBeneficiary() }

            IntermediarySelection.None -> Unit
        }
    }

    private suspend fun submitNewBeneficiary() {
        _events.emit(PickIntermediaryEvents.StartNewIntermediary)
    }

    private suspend fun submitBeneficiary(
        intermediaryId: String,
        intermediaryName: String
    ) {
        createInvoiceManager.intermediaryId = intermediaryId
        createInvoiceManager.intermediaryName = intermediaryName
        _events.emit(PickIntermediaryEvents.Continue)
    }

    private suspend fun submitIgnoringBeneficiary() {
        createInvoiceManager.intermediaryId = null
        createInvoiceManager.intermediaryName = null
        _events.emit(PickIntermediaryEvents.Continue)
    }
}
