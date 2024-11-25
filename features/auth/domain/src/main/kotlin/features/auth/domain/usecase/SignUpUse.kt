package features.auth.domain.usecase

import features.auth.domain.repository.AuthRepository

interface SignUpUse {
    suspend fun invoke(email: String, confirmEmail: String, password: String)
}

internal class SignUpUseImpl(
    private val repository: AuthRepository
) : SignUpUse {
    override suspend fun invoke(
        email: String,
        confirmEmail: String,
        password: String
    ) {
        val tokens = repository.signIn(
            email = email,
            password = password
        )
    }
}