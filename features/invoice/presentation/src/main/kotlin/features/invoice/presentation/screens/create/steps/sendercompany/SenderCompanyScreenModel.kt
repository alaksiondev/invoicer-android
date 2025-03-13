package features.invoice.presentation.screens.create.steps.sendercompany

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

internal class SenderCompanyScreenModel(
    private val manager: CreateInvoiceManager,
    private val dispatcher: CoroutineDispatcher,
) : ScreenModel, foundation.ui.events.EventAware<SenderCompanyEvents> by foundation.ui.events.EventPublisher() {

    private val _state = MutableStateFlow(SenderCompanyState())
    val state: StateFlow<SenderCompanyState> = _state

    fun initScreen() {
        _state.update { oldState ->
            oldState.copy(
                name = manager.senderCompanyName,
                address = manager.senderCompanyAddress
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
                manager.senderCompanyAddress = state.value.address
                manager.senderCompanyName = state.value.name
            }
            publish(SenderCompanyEvents.Continue)
        }
    }
}