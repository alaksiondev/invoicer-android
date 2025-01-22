package features.invoice.presentation.screens.create.steps.confirmation

import features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel

internal data class InvoiceConfirmationState(
    val senderCompanyName: String = "",
    val senderCompanyAddress: String = "",
    val recipientCompanyName: String = "",
    val recipientCompanyAddress: String = "",
    val issueDate: String = "",
    val dueDate: String = "",
    val intermediaryName: String? = null,
    val beneficiaryName: String = "",
    val activities: List<CreateInvoiceActivityUiModel> = listOf(),
    val isLoading: Boolean = false
)

internal sealed interface InvoiceConfirmationEvent {
    data object Success : InvoiceConfirmationEvent
    data class Error(val message: String) : InvoiceConfirmationEvent
}