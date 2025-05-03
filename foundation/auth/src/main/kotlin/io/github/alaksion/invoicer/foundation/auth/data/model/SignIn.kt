package io.github.alaksion.invoicer.foundation.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class AuthTokenResponse(
    val token: String,
    val refreshToken: String
)

@Serializable
internal data class SignInRequest(
    val email: String,
    val password: String,
)

@Serializable
internal data class GoogleSignInRequest(
    val token: String,
)