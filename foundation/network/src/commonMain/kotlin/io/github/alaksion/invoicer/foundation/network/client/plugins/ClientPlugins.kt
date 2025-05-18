package io.github.alaksion.invoicer.foundation.network.client.plugins

import io.github.alaksion.invoicer.foundation.network.NetworkBuildConfig
import io.github.alaksion.invoicer.foundation.network.RequestError
import io.github.alaksion.invoicer.foundation.network.client.InvoicerHttpError
import io.github.alaksion.invoicer.foundation.session.Session
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun HttpClientConfig<*>.setupClient() {
    expectSuccess = true
    contentNegotiation()
    log()
    defaultRequest()
    responseValidation()
}

private fun HttpClientConfig<*>.contentNegotiation() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
        )
    }
}

private fun HttpClientConfig<*>.log() {
    install(Logging)
}

private fun HttpClientConfig<*>.responseValidation() {
    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, request ->
            val clientException =
                exception as? ClientRequestException
                    ?: return@handleResponseExceptionWithRequest

            runCatching {
                clientException.response.body<InvoicerHttpError>()
            }.onSuccess { parsedBody ->
                throw RequestError.Http(
                    httpCode = clientException.response.status.value,
                    errorCode = parsedBody.errorCode,
                    message = parsedBody.message
                )
            }.onFailure {
                throw RequestError.Other(it)
            }
        }
    }
}

private fun HttpClientConfig<*>.defaultRequest() {
    defaultRequest {
        host = NetworkBuildConfig.API_URL
        contentType(ContentType.Application.Json)
        header(HttpHeaders.Authorization, "Bearer " + Session.tokens?.accessToken)
    }
}

