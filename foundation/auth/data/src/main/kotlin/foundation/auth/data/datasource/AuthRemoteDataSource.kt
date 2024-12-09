package foundation.auth.data.datasource

import foundation.auth.data.model.RefreshRequest
import foundation.auth.data.model.RefreshResponse
import foundation.auth.data.model.SignInRequest
import foundation.auth.data.model.SignInResponse
import foundation.auth.data.model.SignUpRequest
import foundation.network.client.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal interface AuthRemoteDataSource {
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

internal class AuthRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : AuthRemoteDataSource {
    override suspend fun signUp(
        email: String,
        confirmEmail: String,
        password: String
    ): String {
        return httpClient
            .post(urlString = baseUrl("/user")) {
                contentType(ContentType.Application.Json)
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
            contentType(ContentType.Application.Json)
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
            contentType(ContentType.Application.Json)
            setBody(
                RefreshRequest(
                    refreshToken = refreshToken
                )
            )
        }.body()
    }
}