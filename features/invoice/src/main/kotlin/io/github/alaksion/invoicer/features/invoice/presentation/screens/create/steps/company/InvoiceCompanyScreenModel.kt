package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company

import cafe.adriel.voyager.core.model.ScreenModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class InvoiceCompanyScreenModel(
    private val manager: CreateInvoiceManager,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel {
    private val _state = MutableStateFlow(InvoiceCompanyState())
    val state = _state.asStateFlow()

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
}