package io.github.alaksion.invoicer.features.invoice.presentation.fakes

import io.github.alaksion.invoicer.features.invoice.domain.model.CreateInvoiceModel
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceDetailsModel
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceList
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceListItem
import io.github.alaksion.invoicer.features.invoice.domain.repository.InvoiceRepository
import kotlinx.datetime.Instant

class FakeInvoiceRepository : InvoiceRepository {

    var getInvoicesFails = false

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
        if (getInvoicesFails) throw Exception("Fake error")

        return InvoiceList(
            items = INVOICE_LIST,
            totalItemCount = INVOICE_LIST.size.toLong(),
            nextPage = null,
        )
    }

    override suspend fun createInvoice(payload: CreateInvoiceModel) = Unit

    override suspend fun getInvoiceDetails(id: String): InvoiceDetailsModel =
        TODO("Not yet implemented")

    companion object {
        val INVOICE_1 = InvoiceListItem(
            id = "123",
            externalId = "Invoice 1",
            senderCompany = "Company A",
            recipientCompany = "Company B",
            issueDate = Instant.parse("2023-10-01T00:00:00Z"),
            dueDate = Instant.parse("2023-10-01T00:00:00Z"),
            createdAt = Instant.parse("2023-10-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T00:00:00Z"),
            totalAmount = 1000
        )

        val INVOICE_2 = InvoiceListItem(
            id = "456",
            externalId = "Invoice 2",
            senderCompany = "Company C",
            recipientCompany = "Company D",
            issueDate = Instant.parse("2023-10-02T00:00:00Z"),
            dueDate = Instant.parse("2023-10-02T00:00:00Z"),
            createdAt = Instant.parse("2023-10-02T00:00:00Z"),
            updatedAt = Instant.parse("2023-10-02T00:00:00Z"),
            totalAmount = 2000
        )

        val INVOICE_LIST = listOf(
            INVOICE_1,
            INVOICE_2
        )
    }
}