package io.github.alaksion.invoicer.foundation.auth.domain.services

import io.github.alaksion.invoicer.foundation.watchers.AuthEvent
import io.github.alaksion.invoicer.foundation.watchers.AuthEventBus
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthTokenRepository
import io.github.alaksion.invoicer.foundation.session.Session
import io.github.alaksion.invoicer.foundation.session.SessionTokens

interface SignInCommandManager {
    suspend fun resolveCommand(type: SignInCommand)
}

sealed interface SignInCommand {
    data class Google(val googleSessionToken: String) : SignInCommand
    data class Credential(val userName: String, val password: String) : SignInCommand
    data object RefreshSession : SignInCommand
}

internal class SignInCommandManagerResolver(
    private val authRepository: AuthRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val authEventBus: AuthEventBus
) : SignInCommandManager {

    override suspend fun resolveCommand(comand: SignInCommand) {
        val authToken = when (comand) {
            is SignInCommand.Credential -> authRepository.signIn(
                email = comand.userName,
                password = comand.password
            )

            is SignInCommand.Google -> authRepository.googleSignIn(comand.googleSessionToken)

            is SignInCommand.RefreshSession -> authRepository.refreshSession(
                refreshToken = authTokenRepository.getAuthTokens()?.refreshToken.orEmpty()
            )
        }

        authTokenRepository.storeAuthTokens(
            accessToken = authToken.accessToken,
            refreshToken = authToken.refreshToken
        )
        Session.tokens = SessionTokens(
            accessToken = authToken.accessToken,
            refreshToken = authToken.refreshToken
        )

        authEventBus.publishEvent(AuthEvent.SignedIn)
    }
}
