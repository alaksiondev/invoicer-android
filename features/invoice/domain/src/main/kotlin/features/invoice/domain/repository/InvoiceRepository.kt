package features.invoice.domain.repository

import features.invoice.domain.model.CreateInvoiceModel
import features.invoice.domain.model.InvoiceDetailsModel
import features.invoice.domain.model.InvoiceList

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
    ): InvoiceList

    suspend fun createInvoice(
        payload: CreateInvoiceModel
    )

    suspend fun getInvoiceDetails(
        id: String
    ): InvoiceDetailsModel
}