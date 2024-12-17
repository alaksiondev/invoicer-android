package features.invoice.domain.repository

import features.invoice.domain.model.InvoiceListItem

interface InvoiceRepository {
    suspend fun getInvoices(
        page: Long,
        limit: Int,
        minIssueDate: String?,
        maxIssueDate: String?,
        minDueDate: String?,
        maxDueDate: String?,
        senderCompany: String?,
        recipientCompany: String?
    ): List<InvoiceListItem>
}