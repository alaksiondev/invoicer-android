package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class InvoiceCompanyScreenModel(
    private val manager: CreateInvoiceManager,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel {
    private val _state = MutableStateFlow(InvoiceCompanyState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<Unit>()
    val events = _events.asSharedFlow()

    fun onSenderNameChange(name: String) {
        _state.value = state.value.copy(senderName = name)
    }

    fun onSenderAddressChange(address: String) {
        _state.value = state.value.copy(senderAddress = address)
    }

    fun onRecipientNameChange(name: String) {
        _state.value = state.value.copy(recipientName = name)
    }

    fun onRecipientAddressChange(address: String) {
        _state.value = state.value.copy(recipientAddress = address)
    }

    fun submit() {
        manager.senderCompanyName = state.value.senderName
        manager.senderCompanyAddress = state.value.senderAddress
        manager.recipientCompanyName = state.value.recipientName
        manager.recipientCompanyAddress = state.value.recipientAddress
        screenModelScope.launch(dispatcher) {
            _events.emit(Unit)
        }
    }
}