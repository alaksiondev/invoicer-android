package foundation.auth.data.fakes

import foundation.auth.data.datasource.AuthRemoteDataSource
import foundation.auth.data.model.RefreshResponse
import foundation.auth.data.model.SignInResponse

internal class FakeAuthRemoteDataSource : AuthRemoteDataSource {

    var signInResponse = SignInResponse(
        token = "token",
        refreshToken = "refreshToken"
    )

    var refreshResponse = RefreshResponse(
        token = "newToken",
        refreshToken = "newRefreshToken"
    )

    var signUpCalls = 0
        private set

    var signInCalls = 0
        private set

    var lastUsedRefreshToken: String? = null

    override suspend fun signUp(email: String, confirmEmail: String, password: String): String {
        signUpCalls++
        return ""
    }

    override suspend fun signIn(email: String, password: String): SignInResponse {
        signInCalls++
        return signInResponse
    }

    override suspend fun refreshToken(refreshToken: String): RefreshResponse {
        lastUsedRefreshToken = refreshToken
        return refreshResponse
    }
}