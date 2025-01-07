package features.invoice.domain.model

import kotlinx.datetime.LocalDate

data class CreateInvoiceModel(
    val externalId: String,
    val senderCompanyName: String,
    val senderCompanyAddress: String,
    val recipientCompanyName: String,
    val recipientCompanyAddress: String,
    val issueDate: LocalDate,
    val dueDate: LocalDate,
    val beneficiaryId: String,
    val intermediaryId: String,
    val activities: List<CreateInvoiceActivityModel> = listOf()
)

data class CreateInvoiceActivityModel(
    val description: String,
    val unitPrice: Long,
    val quantity: Int
)
