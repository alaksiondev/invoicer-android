package features.auth.domain.usecase

import foundation.auth.impl.storage.AuthStorage

interface SignOutUseCase {
    fun signOut()
}

internal class SignOutUseCaseImpl(
    private val authStorage: AuthStorage
) : SignOutUseCase {

    override fun signOut() {
        authStorage.clearTokens()
    }
}