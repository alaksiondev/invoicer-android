package features.auth.domain.usecase

import features.auth.domain.repository.AuthRepository

interface SignUpUseCase {
    suspend fun invoke(email: String, confirmEmail: String, password: String)
}

