package io.github.alaksion.invoicer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import foundation.auth.domain.repository.AuthRepository
import foundation.network.request.handle
import foundation.network.request.launchRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val authStorage: AuthRepository
) : ViewModel() {

    private val _isUserLoggedIN = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIN: StateFlow<Boolean?> = _isUserLoggedIN

    fun startApp() {
        viewModelScope.launch {
            launchRequest {
                authStorage.refreshToken()
            }.handle(
                onStart = {},
                onFinish = {},
                onFailure = {
                    _isUserLoggedIN.update { false }
                },
                onSuccess = {
                    _isUserLoggedIN.update { it != null }
                }
            )
        }
    }
}