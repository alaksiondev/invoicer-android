package foundation.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class SignInResponse(
    val token: String,
    val refreshToken: String
)

@Serializable
internal data class SignInRequest(
    val email: String,
    val password: String,
)