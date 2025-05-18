package io.github.alaksion.invoicer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alaksion.invoicer.foundation.network.request.handle
import io.github.alaksion.invoicer.foundation.network.request.launchRequest
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignInCommand
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignInCommandManager
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignOutService
import io.github.alaksion.invoicer.foundation.utils.logger.InvoicerLogger
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val signInCommandManager: SignInCommandManager,
    private val signOutService: SignOutService,
    private val logger: InvoicerLogger
) : ViewModel() {

    fun startApp() {
        viewModelScope.launch {
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