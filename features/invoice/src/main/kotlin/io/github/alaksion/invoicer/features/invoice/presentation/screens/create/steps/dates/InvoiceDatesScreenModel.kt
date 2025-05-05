package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import io.github.alaksion.invoicer.foundation.utils.date.DateProvider
import io.github.alaksion.invoicer.foundation.utils.date.toZeroHour
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

internal class InvoiceDatesScreenModel(
    private val dateProvider: DateProvider,
    private val dispatcher: CoroutineDispatcher,
    private val manager: CreateInvoiceManager,
) : ScreenModel, EventAware<InvoiceDateEvents> by EventPublisher() {

    private val _state = MutableStateFlow(
        InvoiceDatesState()
    )
    val state: StateFlow<InvoiceDatesState> = _state

    fun initState() {
        _state.update {
            it.copy(
                dueDate = manager.dueDate,
                issueDate = manager.issueDate,
                now = dateProvider.get().toZeroHour(TimeZone.currentSystemDefault())
            )
        }
    }

    fun updateIssueDate(value: Instant) {
        _state.update { oldState ->
            oldState.copy(
                issueDate = value
            )
        }
    }

    fun updateDueDate(value: Instant) {
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