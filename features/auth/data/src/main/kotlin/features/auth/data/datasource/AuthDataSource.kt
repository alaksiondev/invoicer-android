package features.auth.data.datasource

import features.auth.data.model.RefreshRequest
import features.auth.data.model.RefreshResponse
import features.auth.data.model.SignInRequest
import features.auth.data.model.SignInResponse
import features.auth.data.model.SignUpRequest
import foundation.network.client.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal interface AuthDataSource {
    suspend fun signUp(
        email: String,
        confirmEmail: String,
        password: String
    ): String

    suspend fun signIn(
        email: String,
        password: String
    ): SignInResponse

    suspend fun refreshToken(refreshToken: String): RefreshResponse
}

internal class AuthDataSourceImpl(
    private val httpClient: HttpClient
) : AuthDataSource {
    override suspend fun signUp(
        email: String,
        confirmEmail: String,
        password: String
    ): String {
        return httpClient
            .post(urlString = baseUrl("/user")) {
                setBody(
                    SignUpRequest(
                        email = email,
                        confirmEmail = confirmEmail,
                        password = password
                    )
                )
            }
            .body<String>()
    }

    override suspend fun signIn(email: String, password: String): SignInResponse {
        return httpClient.post(
            urlString = baseUrl("/auth/login")
        ) {
            setBody(
                SignInRequest(
                    email = email,
                    password = password
                )
            )
        }.body()
    }

    override suspend fun refreshToken(refreshToken: String): RefreshResponse {
        return httpClient.post(
            urlString = baseUrl("/auth/login")
        ) {
            setBody(
                RefreshRequest(
                    refreshToken = refreshToken
                )
            )
        }.body()
    }
}