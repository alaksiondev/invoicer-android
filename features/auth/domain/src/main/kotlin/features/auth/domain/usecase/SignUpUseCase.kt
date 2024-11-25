package features.auth.domain.usecase

import features.auth.domain.repository.AuthRepository

interface SignUpUseCase {
    suspend fun invoke(email: String, confirmEmail: String, password: String)
}

internal class SignUpUseCaseImpl(
    private val repository: AuthRepository
) : SignUpUseCase {
    override suspend fun invoke(
        email: String,
        confirmEmail: String,
        password: String
    ) {
        val tokens = repository.signUp(
            email = email,
            password = password,
            confirmEmail = confirmEmail
        )
    }
}