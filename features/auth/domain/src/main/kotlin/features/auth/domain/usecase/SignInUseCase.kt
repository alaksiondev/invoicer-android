package features.auth.domain.usecase

import features.auth.domain.repository.AuthRepository
import foundation.auth.impl.storage.AuthStorage

interface SignInUseCase {
    suspend fun signIn(
        email: String,
        password: String
    )
}

internal class SignInUseCaseImpl(
    private val authRepository: AuthRepository,
    private val authStorage: AuthStorage,
) : SignInUseCase {

    override suspend fun signIn(email: String, password: String) {
        val authResponse = authRepository.signIn(
            email = email,
            password = password
        )

        authStorage.storeRefreshToken(token = authResponse.refreshToken)

        authStorage.storeAccessToken(token = authResponse.accessToken)
    }
}