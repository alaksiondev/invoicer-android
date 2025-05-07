package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.confirmation

import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
import io.github.alaksion.invoicer.foundation.utils.money.moneyFormat

internal data class InvoiceConfirmationState(
    val senderCompanyName: String = "",
    val senderCompanyAddress: String = "",
    val recipientCompanyName: String = "",
    val recipientCompanyAddress: String = "",
    val issueDate: String = "",
    val dueDate: String = "",
    val intermediaryName: String? = null,
    val beneficiaryName: String = "",
    val externalId: String = "",
    val activities: List<CreateInvoiceActivityUiModel> = listOf(),
    val isLoading: Boolean = false
) {
    val totalAmount = activities
        .map { it.unitPrice * it.quantity }
        .ifEmpty { listOf(0L) }
        .reduce { acc, amount -> acc + amount }
        .moneyFormat()
}

internal sealed interface InvoiceConfirmationEvent {
    data object Success : InvoiceConfirmationEvent
    data class Error(val message: String) : InvoiceConfirmationEvent
}