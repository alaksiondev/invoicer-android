package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId

internal data class InvoiceExternalIdState(
    val externalId: String = ""
) {
    val isButtonEnabled: Boolean = externalId.isNotEmpty()
}