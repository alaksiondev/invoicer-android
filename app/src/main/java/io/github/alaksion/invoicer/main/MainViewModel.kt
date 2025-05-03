package io.github.alaksion.invoicer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import foundation.network.request.handle
import foundation.network.request.launchRequest
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthTokenRepository
import io.github.alaksion.invoicer.foundation.utils.logger.InvoicerLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val authRepository: AuthRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val logger: InvoicerLogger
) : ViewModel() {

    private val _isUserLoggedIN = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIN: StateFlow<Boolean?> = _isUserLoggedIN

    fun startApp() {
        viewModelScope.launch {
            val tokens = authTokenRepository.getAuthTokens()

            if (tokens == null) {
                logger.logDebug(
                    message = "Failed to refresh token on init: no tokens available",
                    key = TAG,
                )
                _isUserLoggedIN.update { false }
                return@launch
            }

            launchRequest {
                authRepository.refreshSession(tokens.refreshToken)
            }.handle(
                onStart = {},
                onFinish = {},
                onFailure = {
                    logger.logError(
                        message = "Failed to refresh token on init",
                        key = TAG,
                        throwable = it
                    )
                    _isUserLoggedIN.update { false }
                },
                onSuccess = { token ->
                    logger.logDebug(
                        message = "Token refreshed? ${token != null}",
                        key = TAG,
                    )
                    _isUserLoggedIN.update {
                        token != null
                    }
                }
            )
        }
    }

    companion object {
        private const val TAG = "MainViewModel - Refresh"
    }
}