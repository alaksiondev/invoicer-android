package foundation.auth.domain.model

data class StoredTokens(
    val refreshToken: String?,
    val token: String?
)
