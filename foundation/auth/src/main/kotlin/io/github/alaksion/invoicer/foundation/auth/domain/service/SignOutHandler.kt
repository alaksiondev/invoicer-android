package io.github.alaksion.invoicer.foundation.auth.domain.service

import com.google.firebase.auth.FirebaseAuth
import io.github.alaksion.invoicer.foundation.watchers.AuthEvent
import io.github.alaksion.invoicer.foundation.watchers.AuthEventBus
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository
import io.github.alaksion.invoicer.foundation.session.Session

interface SignOutService {
    suspend fun signOut()
}

internal class SignOutHandler(
    private val authRepository: AuthRepository,
    private val authEventBus: AuthEventBus,
    private val firebaseAuth: FirebaseAuth
) : SignOutService {
    override suspend fun signOut() {
        authRepository.signOut()
        Session.tokens = null
        firebaseAuth.signOut()
        authEventBus.publishEvent(AuthEvent.SignedOut)
    }
}
