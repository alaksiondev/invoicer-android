package foundation.network.client

import foundation.network.client.plugins.setupClient
import io.github.alaksion.invoicer.foundation.session.Session
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer

object HttpWrapper {
    val client by lazy {
        HttpClient(OkHttp) {
            setupClient()

            install(Auth) {
                bearer {
                    loadTokens {
                        val tokens = Session.tokens
                        tokens?.let {
                            BearerTokens(
                                accessToken = tokens.accessToken,
                                refreshToken = tokens.refreshToken
                            )
                        }
                    }
                }
            }
        }
    }
}