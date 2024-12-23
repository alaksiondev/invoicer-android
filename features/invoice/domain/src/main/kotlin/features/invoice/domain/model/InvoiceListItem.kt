package features.invoice.domain.model

import kotlinx.datetime.LocalDate

data class InvoiceList(
    val items: List<InvoiceListItem>,
    val totalItemCount: Long,
    val nextPage: Long?
)

data class InvoiceListItem(
    val id: String,
    val externalId: String,
    val senderCompany: String,
    val recipientCompany: String,
    val issueDate: LocalDate,
    val dueDate: LocalDate,
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
    val totalAmount: Long
)
