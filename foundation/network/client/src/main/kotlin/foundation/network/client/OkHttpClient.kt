package foundation.network.client

import foundation.auth.domain.repository.AuthRepository
import foundation.exception.RequestError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class OkHttpClient(
    private val authRepository: AuthRepository
) {
    val client = HttpClient(OkHttp) {
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

        install(Auth) {
            bearer {
                loadTokens {
                    val tokens = authRepository.getTokens()

                    if (tokens.token != null && tokens.refreshToken != null) {
                        BearerTokens(
                            accessToken = tokens.token!!,
                            refreshToken = tokens.refreshToken
                        )
                    } else {
                        null
                    }
                }

                refreshTokens {
                    val refreshedTokens = authRepository.refreshToken() ?: return@refreshTokens null
                    BearerTokens(
                        accessToken = refreshedTokens.accessToken,
                        refreshToken = refreshedTokens.refreshToken
                    )
                }
            }
        }
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
}