package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company

internal data class InvoiceCompanyState(
    val recipientAddress: String = "",
    val recipientName: String = "",
    val senderAddress: String = "",
    val senderName: String = "",
) {
    val isButtonEnabled =
        recipientAddress.isNotEmpty() &&
                recipientName.isNotEmpty() &&
                senderAddress.isNotEmpty() &&
                senderName.isNotEmpty()
}
