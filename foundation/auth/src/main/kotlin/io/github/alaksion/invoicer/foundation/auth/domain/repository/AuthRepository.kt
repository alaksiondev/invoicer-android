package io.github.alaksion.invoicer.foundation.auth.domain.repository

import io.github.alaksion.invoicer.foundation.auth.domain.model.AuthToken

interface AuthRepository {
    suspend fun signUp(
        email: String,
        confirmEmail: String,
        password: String
    )

    suspend fun signIn(
        email: String,
        password: String
    ): AuthToken

    suspend fun googleSignIn(
        token: String
    ): AuthToken

    suspend fun signOut()

    suspend fun refreshSession(
        refreshToken: String,
    ): AuthToken?
}