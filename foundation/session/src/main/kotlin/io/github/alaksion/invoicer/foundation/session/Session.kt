package io.github.alaksion.invoicer.foundation.session

object Session {
    var tokens: SessionTokens? = null
}

data class SessionTokens(
    val accessToken: String,
    val refreshToken: String,
)
