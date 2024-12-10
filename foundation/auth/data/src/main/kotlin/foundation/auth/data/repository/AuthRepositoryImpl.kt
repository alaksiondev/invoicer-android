package foundation.auth.data.repository

import foundation.auth.data.datasource.AuthLocalDataSource
import foundation.auth.data.datasource.AuthRemoteDataSource
import foundation.auth.domain.model.AuthToken
import foundation.auth.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val localDataSource: AuthLocalDataSource,
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun signUp(email: String, confirmEmail: String, password: String) {
        remoteDataSource.signUp(
            email = email,
            confirmEmail = confirmEmail,
            password = password
        )
    }

    override suspend fun signIn(email: String, password: String) {
        val session = remoteDataSource.signIn(
            email = email,
            password = password
        )

        localDataSource.storeAccessToken(session.token)
        localDataSource.storeRefreshToken(session.refreshToken)
    }

    override suspend fun signOut() {
        localDataSource.clearTokens()
    }

    override suspend fun refreshToken(): AuthToken? {
        val refreshToken = localDataSource.getRefreshToken() ?: return null

        val refreshedSession = remoteDataSource.refreshToken(refreshToken)
        return AuthToken(
            accessToken = refreshedSession.token,
            refreshToken = refreshedSession.refreshToken
        )
    }
}