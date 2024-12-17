package features.invoice.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
internal data class InvoiceListItemResponse(
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

internal fun InvoiceListItemResponse.toDomainModel() = features.invoice.domain.model.InvoiceListItem(
    id = id,
    externalId = externalId,
    senderCompany = senderCompany,
    recipientCompany = recipientCompany,
    issueDate = issueDate,
    dueDate = dueDate,
    createdAt = createdAt,
    updatedAt = updatedAt,
    totalAmount = totalAmount
)
