package io.github.alaksion.invoicer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import foundation.auth.domain.repository.AuthRepository
import foundation.network.request.handle
import foundation.network.request.launchRequest
import io.github.alaksion.invoicer.foundation.utils.logger.InvoicerLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val authStorage: AuthRepository,
    private val logger: InvoicerLogger
) : ViewModel() {

    private val _isUserLoggedIN = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIN: StateFlow<Boolean?> = _isUserLoggedIN

    fun startApp() {
        viewModelScope.launch {
            launchRequest {
                authStorage.refreshSession()
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