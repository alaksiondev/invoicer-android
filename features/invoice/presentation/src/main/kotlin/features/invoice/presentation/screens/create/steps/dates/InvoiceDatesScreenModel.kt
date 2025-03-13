package features.invoice.presentation.screens.create.steps.dates

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.invoice.presentation.screens.create.CreateInvoiceManager
import foundation.date.impl.DateProvider
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class InvoiceDatesScreenModel(
    private val dateProvider: DateProvider,
    private val dispatcher: CoroutineDispatcher,
    private val manager: CreateInvoiceManager,
) : ScreenModel, foundation.ui.events.EventAware<InvoiceDateEvents> by foundation.ui.events.EventPublisher() {

    private val _state = MutableStateFlow(
        InvoiceDatesState()
    )
    val state: StateFlow<InvoiceDatesState> = _state

    fun initState() {
        _state.update {
            it.copy(
                dueDate = manager.dueDate,
                issueDate = manager.issueDate,
                now = dateProvider.get().toEpochMilliseconds()
            )
        }
    }

    fun updateIssueDate(value: Long) {
        _state.update { oldState ->
            oldState.copy(
                issueDate = value
            )
        }
    }

    fun updateDueDate(value: Long) {
        _state.update { oldState ->
            oldState.copy(
                dueDate = value
            )
        }
    }

    fun submit() {
        screenModelScope.launch(dispatcher) {
            if (_state.value.formValid) {
                manager.dueDate = _state.value.dueDate
                manager.issueDate = _state.value.issueDate
            }
            publish(InvoiceDateEvents.Continue)
        }
    }
}