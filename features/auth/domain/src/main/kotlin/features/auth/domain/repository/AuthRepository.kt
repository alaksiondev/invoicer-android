package features.auth.domain.repository

import features.auth.domain.model.AuthToken

interface AuthRepository {
    suspend fun signUp(
        email: String,
        confirmEmail: String,
        password: String
    ): String

    suspend fun signIn(
        email: String,
        password: String
    ): AuthToken

    suspend fun refreshToken(refreshToken: String): AuthToken
}