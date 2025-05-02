package io.github.alaksion.invoicer.foundation.auth.domain.service

import foundation.watchers.AuthEvent
import foundation.watchers.AuthEventBus
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthTokenRepository

interface SignInCommandManager {
    suspend fun resolveCommand(type: SignInCommand)
}

sealed interface SignInCommand {
    data class Google(val googleSessionToken: String) : SignInCommand
    data class Credential(val userName: String, val password: String) : SignInCommand
}

internal class SignInCommandManagerResolver(
    private val authRepository: AuthRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val authEventBus: AuthEventBus
) : SignInCommandManager {

    override suspend fun resolveCommand(type: SignInCommand) {
        val authToken = when (type) {
            is SignInCommand.Credential -> authRepository.signIn(
                email = type.userName,
                password = type.password
            )

            is SignInCommand.Google -> authRepository.googleSignIn(type.googleSessionToken)
        }

        authTokenRepository.storeAuthTokens(
            accessToken = authToken.accessToken,
            refreshToken = authToken.refreshToken
        )
        authEventBus.publishEvent(AuthEvent.SignedIn)
    }
}