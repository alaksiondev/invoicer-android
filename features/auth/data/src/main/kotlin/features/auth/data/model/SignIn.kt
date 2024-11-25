package features.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class SignInResponse(
    val accessToken: String,
    val refreshToken: String
)

@Serializable
internal data class SignInRequest(
    val email: String,
    val password: String,
)