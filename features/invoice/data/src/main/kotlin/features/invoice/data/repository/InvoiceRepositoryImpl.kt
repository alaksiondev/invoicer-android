package features.invoice.data.repository

import features.invoice.data.datasource.InvoiceDataSource
import features.invoice.data.model.toDomainModel
import features.invoice.domain.model.InvoiceList
import features.invoice.domain.repository.InvoiceRepository

internal class InvoiceRepositoryImpl(
    private val dataSource: InvoiceDataSource
) : InvoiceRepository {

    override suspend fun getInvoices(
        page: Long,
        limit: Int,
        minIssueDate: String?,
        maxIssueDate: String?,
        minDueDate: String?,
        maxDueDate: String?,
        senderCompany: String?,
        recipientCompany: String?
    ): InvoiceList {
        return dataSource.getInvoices(
            page = page,
            limit = limit,
            minIssueDate = minIssueDate,
            maxIssueDate = maxIssueDate,
            minDueDate = minDueDate,
            maxDueDate = maxDueDate,
            senderCompany = senderCompany,
            recipientCompany = recipientCompany
        ).toDomainModel()
    }
}