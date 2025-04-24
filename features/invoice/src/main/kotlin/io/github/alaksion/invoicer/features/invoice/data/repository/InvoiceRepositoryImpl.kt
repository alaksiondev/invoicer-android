package features.invoice.data.repository

import io.github.alaksion.invoicer.features.invoice.data.datasource.InvoiceDataSource
import io.github.alaksion.invoicer.features.invoice.data.model.toDataModel
import io.github.alaksion.invoicer.features.invoice.data.model.toDomainModel
import io.github.alaksion.invoicer.features.invoice.domain.model.CreateInvoiceModel
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceDetailsModel
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceList
import io.github.alaksion.invoicer.features.invoice.domain.repository.InvoiceRepository

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

    override suspend fun createInvoice(payload: CreateInvoiceModel) {
        return dataSource.createInvoice(
            payload = payload.toDataModel()
        )
    }

    override suspend fun getInvoiceDetails(id: String): InvoiceDetailsModel {
        return dataSource.getInvoiceDetails(invoiceId = id).toDomainModel()
    }
}