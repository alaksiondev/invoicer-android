package foundation.network.client.plugins

import foundation.exception.RequestError
import foundation.network.client.BASE_URL
import foundation.network.client.BuildConfig
import foundation.network.client.InvoicerHttpError
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun HttpClientConfig<OkHttpConfig>.setupClient() {
    expectSuccess = true
    contentNegotiation()
    log()
    defaultRequest()
    responseValidation()
}

private fun HttpClientConfig<OkHttpConfig>.contentNegotiation() {
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

private fun HttpClientConfig<OkHttpConfig>.log() {
    install(Logging) {
        val logLevel = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
        logger = Logger.DEFAULT
        level = logLevel
    }
}

private fun HttpClientConfig<OkHttpConfig>.responseValidation() {
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

private fun HttpClientConfig<OkHttpConfig>.defaultRequest() {
    defaultRequest {
        header("Content-Type", "application/json")
        host = BASE_URL
    }
}

