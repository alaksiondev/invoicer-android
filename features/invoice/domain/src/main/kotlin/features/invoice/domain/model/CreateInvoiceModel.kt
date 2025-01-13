package features.invoice.domain.model

import kotlinx.datetime.Instant

data class CreateInvoiceModel(
    val externalId: String,
    val senderCompanyName: String,
    val senderCompanyAddress: String,
    val recipientCompanyName: String,
    val recipientCompanyAddress: String,
    val issueDate: Instant,
    val dueDate: Instant,
    val beneficiaryId: String,
    val intermediaryId: String,
    val activities: List<CreateInvoiceActivityModel> = listOf()
)

data class CreateInvoiceActivityModel(
    val description: String,
    val unitPrice: Long,
    val quantity: Int
)
