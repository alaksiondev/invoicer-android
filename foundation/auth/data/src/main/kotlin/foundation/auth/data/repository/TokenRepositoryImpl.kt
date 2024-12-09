package foundation.auth.data.repository

import foundation.auth.data.datasource.AuthLocalDataSource
import foundation.auth.domain.model.StoredTokens
import foundation.auth.domain.repository.TokenRepository

internal class TokenRepositoryImpl(
    private val localDataSource: AuthLocalDataSource
) : TokenRepository {
    override suspend fun getTokens(): StoredTokens {
        return StoredTokens(
            refreshToken = localDataSource.getRefreshToken(),
            token = localDataSource.getAccessToken()
        )
    }
}