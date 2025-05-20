package io.github.alaksion.invoicer.features.auth.presentation.fakes

import io.github.alaksion.invoicer.foundation.auth.domain.services.SignInCommand
import io.github.alaksion.invoicer.foundation.auth.domain.services.SignInCommandManager

class FakeSignInCommander : SignInCommandManager {

    var failure: Throwable? = null
    var signInCommand: SignInCommand? = null

    override suspend fun resolveCommand(type: SignInCommand) {
        failure?.let { throw it }
        signInCommand = type
    }
}