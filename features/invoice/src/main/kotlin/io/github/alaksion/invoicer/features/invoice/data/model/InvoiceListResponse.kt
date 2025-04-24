package io.github.alaksion.invoicer.features.invoice.data.model

import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceList
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceListItem
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
internal data class InvoiceListResponse(
    val items: List<InvoiceListItemResponse>,
    val totalItemCount: Long,
    val nextPage: Long?
)

@Serializable
internal data class InvoiceListItemResponse(
    val id: String,
    val externalId: String,
    val senderCompany: String,
    val recipientCompany: String,
    val issueDate: Instant,
    val dueDate: Instant,
    val createdAt: Instant,
    val updatedAt: Instant,
    val totalAmount: Long
)

internal fun InvoiceListResponse.toDomainModel() = InvoiceList(
    items = items.map {
        InvoiceListItem(
            id = it.id,
            externalId = it.externalId,
            senderCompany = it.senderCompany,
            recipientCompany = it.recipientCompany,
            issueDate = it.issueDate,
            dueDate = it.dueDate,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            totalAmount = it.totalAmount
        )
    },
    totalItemCount = totalItemCount,
    nextPage = nextPage
)
