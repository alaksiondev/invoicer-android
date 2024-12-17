package features.invoice.domain.model

import kotlinx.datetime.LocalDate

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
