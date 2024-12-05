package features.auth.data.repository

import features.auth.data.datasource.AuthDataSource
import features.auth.domain.model.AuthToken
import features.auth.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val dataSource: AuthDataSource
) : AuthRepository {

    override suspend fun signUp(email: String, confirmEmail: String, password: String): String {
        return dataSource.signUp(
            email = email,
            confirmEmail = confirmEmail,
            password = password
        )
    }

    override suspend fun signIn(email: String, password: String): AuthToken {
        val response = dataSource.signIn(
            email = email,
            password = password
        )
        return AuthToken(
            accessToken = response.token,
            refreshToken = response.refreshToken
        )
    }

    override suspend fun refreshToken(refreshToken: String): AuthToken {
        val response = dataSource.refreshToken(
            refreshToken = refreshToken
        )
        return AuthToken(
            accessToken = response.token,
            refreshToken = response.refreshToken
        )
    }
}