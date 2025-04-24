package foundation.auth.data.datasource

import foundation.auth.data.model.GoogleSignInRequest
import foundation.auth.data.model.RefreshRequest
import foundation.auth.data.model.RefreshResponse
import foundation.auth.data.model.SignInRequest
import foundation.auth.data.model.SignInResponse
import foundation.auth.data.model.SignUpRequest
import foundation.network.client.HttpWrapper
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

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

    suspend fun googleSignIn(
        token: String
    ): SignInResponse
}

internal class AuthRemoteDataSourceImpl(
    private val httpWrapper: HttpWrapper,
    private val dispatcher: CoroutineDispatcher,
) : AuthRemoteDataSource {
    override suspend fun signUp(
        email: String,
        confirmEmail: String,
        password: String
    ): String {
        return withContext(dispatcher) {
            httpWrapper.client
                .post(
                    urlString = "/v1/user"
                ) {
                    setBody(
                        SignUpRequest(
                            email = email,
                            confirmEmail = confirmEmail,
                            password = password
                        )
                    )
                }.body<String>()
        }
    }

    override suspend fun signIn(email: String, password: String): SignInResponse {
        return withContext(dispatcher) {
            httpWrapper.client.post(
                urlString = "/v1/auth/login"
            ) {
                setBody(
                    SignInRequest(
                        email = email,
                        password = password
                    )
                )
            }.body()
        }
    }

    override suspend fun refreshToken(refreshToken: String): RefreshResponse {
        return withContext(dispatcher) {
            httpWrapper.client.post(
                urlString = "/v1/auth/refresh"
            ) {
                setBody(
                    RefreshRequest(
                        refreshToken = refreshToken
                    )
                )
            }.body()
        }
    }

    override suspend fun googleSignIn(token: String): SignInResponse {
        return withContext(dispatcher) {
            httpWrapper.client.post(
                urlString = "/v1/auth/google"
            ) {
                setBody(
                    GoogleSignInRequest(
                        token = token
                    )
                )
            }.body()
        }
    }
}