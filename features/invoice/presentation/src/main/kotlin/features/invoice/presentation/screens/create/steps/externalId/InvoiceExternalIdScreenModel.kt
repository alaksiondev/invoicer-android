package features.invoice.presentation.screens.create.steps.externalId

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.invoice.presentation.screens.create.CreateInvoiceManager
import foundation.events.EventAware
import foundation.events.EventPublisher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class InvoiceExternalIdScreenModel(
    private val manager: CreateInvoiceManager,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel, EventAware<InvoiceExternalIdEvents> by EventPublisher() {

    private val _state = MutableStateFlow(InvoiceExternalIdState())
    val state: StateFlow<InvoiceExternalIdState> = _state

    fun initState() {
        _state.update {
            it.copy(
                externalId = manager.externalId
            )
        }
    }

    fun updateExternalId(value: String) {
        _state.update {
            it.copy(
                externalId = value
            )
        }
    }

    fun submit() {
        if (_state.value.isEnabled) screenModelScope.launch(dispatcher) {
            manager.externalId = _state.value.externalId
            publish(InvoiceExternalIdEvents.Continue)
        }
    }

}