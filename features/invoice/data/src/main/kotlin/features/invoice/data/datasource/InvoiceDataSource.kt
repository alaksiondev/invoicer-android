package features.invoice.data.datasource

import features.invoice.data.model.CreateInvoiceRequest
import features.invoice.data.model.InvoiceDetailsResponse
import features.invoice.data.model.InvoiceListResponse
import foundation.network.client.BASE_URL
import foundation.network.client.HttpWrapper
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.buildUrl
import io.ktor.http.contentType
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
    ): InvoiceListResponse

    suspend fun createInvoice(
        payload: CreateInvoiceRequest
    ): Unit

    suspend fun getInvoiceDetails(
        invoiceId: String
    ): InvoiceDetailsResponse
}

internal class InvoiceDataSourceImpl(
    private val httpWrapper: HttpWrapper,
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
    ): InvoiceListResponse {
        return withContext(dispatcher) {
            val url = buildUrl {
                host = BASE_URL
                path("/v1/invoice")
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

            httpWrapper.client.get(url = url).body()
        }
    }

    override suspend fun createInvoice(
        payload: CreateInvoiceRequest
    ) {
        withContext(dispatcher) {
            val url = buildUrl {
                host = BASE_URL
                path("/v1/invoice")
            }
            httpWrapper.client.post(
                url = url
            ) {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
        }
    }

    override suspend fun getInvoiceDetails(invoiceId: String): InvoiceDetailsResponse {
        return withContext(dispatcher) {
            val url = buildUrl {
                host = BASE_URL
                path("/v1/invoice/$invoiceId")
            }

            httpWrapper.client.get(url = url) {
                contentType(ContentType.Application.Json)
            }.body()
        }
    }
}