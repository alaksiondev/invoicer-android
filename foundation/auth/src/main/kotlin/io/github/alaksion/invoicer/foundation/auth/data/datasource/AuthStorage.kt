package io.github.alaksion.invoicer.foundation.auth.data.datasource

import foundation.storage.impl.KeyStoreManager
import foundation.storage.impl.LocalStorage
import io.github.alaksion.invoicer.foundation.auth.domain.model.AuthToken

internal interface AuthStorage {
    suspend fun storeAuthTokens(
        accessToken: String,
        refreshToken: String
    )

    suspend fun getAuthTokens(): AuthToken?

    suspend fun clearTokens()
}

internal class AuthStorageImpl(
    private val localStorage: LocalStorage,
    private val keyStoreManager: KeyStoreManager
) : AuthStorage {

    override suspend fun storeAuthTokens(accessToken: String, refreshToken: String) {
        val encryptAccessToken = keyStoreManager.encryptValue(accessToken)
        val encryptRefreshToken = keyStoreManager.encryptValue(refreshToken)
        localStorage.setString(
            key = REFRESH_TOKEN_KEY,
            value = encryptRefreshToken
        )

        localStorage.setString(
            key = ACCESS_TOKEN_KEY,
            value = encryptAccessToken
        )
    }

    override suspend fun getAuthTokens(): AuthToken? {
        val refreshToken = localStorage.getString(REFRESH_TOKEN_KEY)
        val accessToken = localStorage.getString(ACCESS_TOKEN_KEY)

        if (refreshToken == null || accessToken == null) {
            return null
        }

        val decryptAccessToken = keyStoreManager.decryptValue(accessToken)
        val decryptRefreshToken = keyStoreManager.encryptValue(refreshToken)

        return AuthToken(
            refreshToken = decryptRefreshToken,
            accessToken = decryptAccessToken
        )
    }

    override suspend fun clearTokens() {
        localStorage.clear(REFRESH_TOKEN_KEY)
        localStorage.clear(ACCESS_TOKEN_KEY)
    }

    private companion object {
        const val REFRESH_TOKEN_KEY = "invoicer-refresh"
        const val ACCESS_TOKEN_KEY = "invoicer-access"
    }
}