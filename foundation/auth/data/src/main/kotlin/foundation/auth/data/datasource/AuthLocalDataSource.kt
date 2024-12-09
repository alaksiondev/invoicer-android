package foundation.auth.data.datasource

import foundation.storage.impl.LocalStorage


interface AuthLocalDataSource {
    fun storeRefreshToken(token: String)
    fun storeAccessToken(token: String)
    fun clearTokens()
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
}

internal class AuthLocalDataSourceImpl(
    private val localStorage: LocalStorage
) : AuthLocalDataSource {

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

    override fun getAccessToken(): String? {
        return localStorage.getString(ACCESS_TOKEN_KEY)
    }

    override fun getRefreshToken(): String? {
        return localStorage.getString(REFRESH_TOKEN_KEY)
    }

    companion object {
        val REFRESH_TOKEN_KEY = "invoicer-refresh"
        val ACCESS_TOKEN_KEY = "invoicer-access"
    }
}
