package io.github.alaksion.invoicer.features.auth.presentation.screens.startup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.foundation.auth.domain.services.SignInCommand
import io.github.alaksion.invoicer.foundation.auth.domain.services.SignInCommandManager
import io.github.alaksion.invoicer.foundation.auth.domain.services.SignOutService
import io.github.alaksion.invoicer.foundation.network.request.handle
import io.github.alaksion.invoicer.foundation.network.request.launchRequest
import io.github.alaksion.invoicer.foundation.utils.logger.InvoicerLogger
import kotlinx.coroutines.launch

internal class StartupScreenModel(
    private val signInCommandManager: SignInCommandManager,
    private val signOutService: SignOutService,
    private val logger: InvoicerLogger
) : ScreenModel {

    fun startApp() {
        screenModelScope.launch {
            launchRequest {
                signInCommandManager.resolveCommand(
                    SignInCommand.RefreshSession
                )
            }.handle(
                onFailure = {
                    logger.logError(
                        message = "Failed to refresh token on init",
                        key = TAG,
                        throwable = it
                    )
                    signOutService.signOut()
                },
            )
        }
    }

    companion object {
        private const val TAG = "MainViewModel - Refresh"
    }
}