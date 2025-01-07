package features.invoice.presentation.screens.create.steps.sendercompany

import cafe.adriel.voyager.core.model.ScreenModel
import features.invoice.presentation.screens.create.CreateInvoiceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

internal class SenderCompanyScreenModel(
    private val manager: CreateInvoiceManager
) : ScreenModel {

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
        if (state.value.isFormValid) {
            manager.senderCompanyAddress = state.value.address
            manager.senderCompanyName = state.value.name
        }
    }
}