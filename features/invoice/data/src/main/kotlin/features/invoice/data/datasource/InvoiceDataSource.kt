package features.invoice.data.datasource

import features.invoice.data.model.InvoiceListItemResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.buildUrl
import io.ktor.http.path

internal interface InvoiceDataSource {
    suspend fun getInvoices(
        page: Long,
        limit: Int,
        minIssueDate: String?,
        maxIssueDate: String?,
        minDueDate: String?,
        maxDueDate: String?,
        senderCompany: String?,
        recipientCompany: String?
    ): List<InvoiceListItemResponse>
}

internal class InvoiceDataSourceImpl(
    private val httpClient: HttpClient
) : InvoiceDataSource {

    override suspend fun getInvoices(
        page: Long,
        limit: Int,
        minIssueDate: String?,
        maxIssueDate: String?,
        minDueDate: String?,
        maxDueDate: String?,
        senderCompany: String?,
        recipientCompany: String?
    ): List<InvoiceListItemResponse> {
        return httpClient.get(
            url = buildUrl {
                path("/invoice")
                parameters.apply {
                    append("page", page.toString())
                    append("limit", limit.toString())
                    minIssueDate?.let { append("minIssueDate", it) }
                    maxIssueDate?.let { append("maxIssueDate", it) }
                    minDueDate?.let { append("minDueDate", it) }
                    maxDueDate?.let { append("maxDueDate", it) }
                    senderCompany?.let { append("senderCompany", it) }
                    recipientCompany?.let { append("recipientCompany", it) }
                }
            }
        ).body()
    }

}