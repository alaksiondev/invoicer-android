package features.auth.domain.storage

import foundation.storage.impl.LocalStorage

internal interface AuthStorage {
    fun storeRefreshToken(token: String)
    fun storeAccessToken(token: String)
    fun clearTokens()
}

internal class AuthStorageImpl(
    private val localStorage: LocalStorage
) : AuthStorage {

    override fun storeRefreshToken(token: String) {
        localStorage.setString(
            value = token,
            key = REFRESH_TOKEN_KEY
        )
    }

    override fun storeAccessToken(token: String) {
        localStorage.setString(
            value = token,
            key = ACCESS_TOKEN_KEY
        )
    }

    override fun clearTokens() {
        localStorage.clear(REFRESH_TOKEN_KEY)
        localStorage.clear(ACCESS_TOKEN_KEY)
    }

    companion object {
        val REFRESH_TOKEN_KEY = "invoicer-refresh"
        val ACCESS_TOKEN_KEY = "invoicer-access"
    }

}