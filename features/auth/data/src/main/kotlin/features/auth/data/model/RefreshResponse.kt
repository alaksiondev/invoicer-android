package features.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String
)

@Serializable
internal data class RefreshRequest(
    val refreshToken: String,
)

