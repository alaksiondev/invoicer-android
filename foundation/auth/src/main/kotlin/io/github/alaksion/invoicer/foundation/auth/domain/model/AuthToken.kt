package io.github.alaksion.invoicer.foundation.auth.domain.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)
