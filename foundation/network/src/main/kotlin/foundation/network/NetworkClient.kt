package foundation.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class NetworkClientImpl(
    private val httpClient: HttpClient
) {
    suspend inline fun <reified Request, reified Response> post(
        requestHeaders: Map<String, String>,
        request: Request,
        urlString: String
    ): Response {
        return httpClient.post(urlString = BuildConfig.API_URL + urlString) {
            setCustomHeaders(requestHeaders)
            contentType(ContentType.Application.Json)
            setBody(request)

        }.body()
    }

    suspend inline fun <reified Response> get(
        requestHeaders: Map<String, String>,
        urlString: String,
    ): Response {
        return httpClient.get(urlString = BuildConfig.API_URL + urlString) {
            setCustomHeaders(requestHeaders)
            contentType(ContentType.Application.Json)
        }.body()
    }

    private fun HttpRequestBuilder.setCustomHeaders(requestHeaders: Map<String, String>) {
        requestHeaders.forEach {
            headers.append(
                name = it.key,
                value = it.value
            )
        }
    }
}