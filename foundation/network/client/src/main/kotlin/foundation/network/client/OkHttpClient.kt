package foundation.network.client

import foundation.exception.RequestError
import foundation.network.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal val okHttpClient = HttpClient(OkHttp) {
    expectSuccess = true

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
        )
    }

    install(Logging) {
        val logLevel = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
        logger = Logger.DEFAULT
        level = logLevel
    }

    HttpResponseValidator {
        validateResponse { response ->
            runCatching {
                response.body<InvoicerHttpError>()
            }.onSuccess { parsedBody ->
                throw RequestError.Http(
                    httpCode = response.status.value,
                    errorCode = parsedBody.errorCode,
                    message = parsedBody.message
                )
            }.onFailure {
                throw RequestError.Other(it)
            }
        }
    }

}