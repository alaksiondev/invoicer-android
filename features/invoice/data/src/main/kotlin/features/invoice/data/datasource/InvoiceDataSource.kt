package features.invoice.data.datasource

import features.invoice.data.model.InvoiceListItemResponse
import foundation.network.client.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.buildUrl
import io.ktor.http.path
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

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
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
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
        return withContext(dispatcher) {
            val url = buildUrl {
                host = BASE_URL
                path("/ invoice")
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

            httpClient.get(url = url).body()
        }
    }
}