package foundation.network.client

import foundation.auth.domain.repository.TokenRepository
import foundation.network.client.plugins.setupClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer

class HttpWrapper(
    private val tokenRepository: TokenRepository
) {
    val client by lazy {
        HttpClient(OkHttp) {
            setupClient()

            install(Auth) {
                bearer {
                    loadTokens {
                        val tokens = tokenRepository.getTokens()

                        if (tokens.token != null && tokens.refreshToken != null) {
                            BearerTokens(
                                accessToken = tokens.token!!,
                                refreshToken = tokens.refreshToken
                            )
                        } else {
                            null
                        }
                    }
                }
            }
        }
    }
}

class RefreshHttpWrapper {
    val client by lazy {
        HttpClient(OkHttp) {
            setupClient()
        }
    }
}