package io.github.alaksion.invoicer.foundation.auth.data.repository

import io.github.alaksion.invoicer.foundation.auth.data.datasource.AuthRemoteDataSource
import io.github.alaksion.invoicer.foundation.auth.data.datasource.AuthStorage
import io.github.alaksion.invoicer.foundation.auth.domain.model.AuthToken
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val authStorage: AuthStorage
) : AuthRepository {
    override suspend fun signUp(email: String, confirmEmail: String, password: String) {
        remoteDataSource.signUp(
            email = email,
            confirmEmail = confirmEmail,
            password = password
        )
    }

    override suspend fun signIn(email: String, password: String): AuthToken {
        val response = remoteDataSource.signIn(
            email = email,
            password = password
        )

        return AuthToken(
            refreshToken = response.refreshToken,
            accessToken = response.token
        )
    }

    override suspend fun googleSignIn(token: String): AuthToken {
        val response = remoteDataSource.googleSignIn(
            token = token
        )

        return AuthToken(
            refreshToken = response.refreshToken,
            accessToken = response.token
        )
    }

    override suspend fun signOut() {
        authStorage.clearTokens()

    }

    override suspend fun refreshSession(
        refreshToken: String
    ): AuthToken {
        val refreshedSession = remoteDataSource.refreshToken(refreshToken)

        return AuthToken(
            accessToken = refreshedSession.token,
            refreshToken = refreshedSession.refreshToken
        )
    }
}
