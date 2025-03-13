package features.invoice.presentation.screens.create.steps.recipientcompany

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.invoice.presentation.screens.create.CreateInvoiceManager
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class RecipientCompanyScreenModel(
    private val manager: CreateInvoiceManager,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel, foundation.ui.events.EventAware<RecipientCompanyEvents> by foundation.ui.events.EventPublisher() {

    private val _state = MutableStateFlow(RecipientCompanyState())
    val state: StateFlow<RecipientCompanyState> = _state

    fun initScreen() {
        _state.update { oldState ->
            oldState.copy(
                name = manager.recipientCompanyName,
                address = manager.recipientCompanyAddress
            )
        }
    }

    fun updateName(value: String) {
        _state.update { oldState ->
            oldState.copy(
                name = value
            )
        }
    }

    fun updateAddress(value: String) {
        _state.update { oldState ->
            oldState.copy(
                address = value
            )
        }
    }

    fun submit() {
        screenModelScope.launch(dispatcher) {
            if (state.value.isFormValid) {
                manager.recipientCompanyAddress = state.value.address
                manager.recipientCompanyName = state.value.name
            }
            publish(RecipientCompanyEvents.Continue)
        }
    }
}