package io.github.alaksion.invoicer.features.invoice.presentation.screens.details

import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceDetailsActivityModel
import kotlinx.datetime.Instant

internal data class InvoiceDetailsState(
    val externalId: String = "",
    val senderCompany: String = "",
    val recipientCompany: String = "",
    val issueDate: Instant = Instant.DISTANT_PAST,
    val dueDate: Instant = Instant.DISTANT_PAST,
    val beneficiary: String = "",
    val intermediary: String? = null,
    val createdAt: Instant = Instant.DISTANT_PAST,
    val updatedAt: Instant = Instant.DISTANT_PAST,
    val activities: List<InvoiceDetailsActivityModel> = listOf(),
    val mode: InvoiceDetailsMode = InvoiceDetailsMode.Content,
)

internal sealed interface InvoiceDetailsMode {
    data object Content : InvoiceDetailsMode
    data object Loading : InvoiceDetailsMode
    data class Error(
        val errorType: InvoiceDetailsErrorType
    ) : InvoiceDetailsMode
}

internal enum class InvoiceDetailsErrorType {
    NotFound,
    Generic
}
