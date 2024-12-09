package foundation.auth.domain.repository

import foundation.auth.domain.model.AuthToken

interface AuthRepository {
    suspend fun signUp(
        email: String,
        confirmEmail: String,
        password: String
    )

    suspend fun signIn(
        email: String,
        password: String
    )

    suspend fun refreshToken(): AuthToken?
}