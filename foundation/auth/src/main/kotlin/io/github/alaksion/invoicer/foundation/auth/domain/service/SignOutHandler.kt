package io.github.alaksion.invoicer.foundation.auth.domain.service

import foundation.watchers.AuthEvent
import foundation.watchers.AuthEventBus
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository

interface SignOutService {
    suspend fun signOut()
}

internal class SignOutHandler(
    private val authRepository: AuthRepository,
    private val authEventBus: AuthEventBus
) : SignOutService {
    override suspend fun signOut() {
        authRepository.signOut()
        authEventBus.publishEvent(AuthEvent.SignedOut)
    }
}