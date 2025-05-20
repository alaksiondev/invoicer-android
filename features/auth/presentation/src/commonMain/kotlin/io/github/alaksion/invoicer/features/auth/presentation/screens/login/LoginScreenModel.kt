package io.github.alaksion.invoicer.features.auth.presentation.screens.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsTracker
import io.github.alaksion.invoicer.foundation.auth.domain.services.SignInCommand
import io.github.alaksion.invoicer.foundation.auth.domain.services.SignInCommandManager
import io.github.alaksion.invoicer.foundation.network.RequestError
import io.github.alaksion.invoicer.foundation.network.request.handle
import io.github.alaksion.invoicer.foundation.network.request.launchRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginScreenModel(
    private val signInCommander: SignInCommandManager,
    private val dispatcher: CoroutineDispatcher,
    private val analyticsTracker: AnalyticsTracker
) : ScreenModel {

    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state

    private val _events = MutableSharedFlow<LoginScreenEvents>()
    val events = _events.asSharedFlow()

    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun toggleCensorship() {
        _state.value = _state.value.copy(censored = !_state.value.censored)
    }

    fun submitIdentityLogin() {
        if (_state.value.buttonEnabled) handleSignInRequest()
    }

    fun launchGoogleLogin() {
        screenModelScope.launch(dispatcher) {
            analyticsTracker.track(LoginAnalytics.GoogleLoginStarted)
            _state.update {
                it.copy(
                    isGoogleLoading = true
                )
            }
            _events.emit(LoginScreenEvents.LaunchGoogleLogin)
        }
    }

    private fun handleSignInRequest() {
        screenModelScope.launch {
            launchRequest {
                signInCommander.resolveCommand(
                    SignInCommand.Credential(
                        userName = _state.value.email,
                        password = _state.value.password
                    )
                )
            }.handle(
                onStart = {
                    analyticsTracker.track(LoginAnalytics.IdentityLoginStarted)
                    _state.update {
                        it.copy(isSignInLoading = true)
                    }
                },
                onFinish = { _state.update { it.copy(isSignInLoading = false) } },
                onFailure = {
                    analyticsTracker.track(LoginAnalytics.IdentityLoginFailure)
                    sendErrorEvent(it)
                },
                onSuccess = {
                    analyticsTracker.track(LoginAnalytics.IdentityLoginSuccess)
                }
            )
        }
    }

    private suspend fun sendErrorEvent(error: RequestError) {
        val message = when (error) {
            is RequestError.Http -> error.message?.let {
                LoginScreenEvents.Failure(it)
            } ?: LoginScreenEvents.GenericFailure

            is RequestError.Other -> LoginScreenEvents.GenericFailure
        }
        _events.emit(message)
    }

    fun cancelGoogleSignIn() {
        analyticsTracker.track(LoginAnalytics.GoogleLoginFailure)
        _state.update {
            it.copy(
                isGoogleLoading = false
            )
        }
    }

    fun handleGoogleError(error: Throwable) {
        screenModelScope.launch(dispatcher) {
            analyticsTracker.track(LoginAnalytics.GoogleLoginFailure)
            _events.emit(
                LoginScreenEvents.Failure(
                    message = error.message.orEmpty()
                )
            )
        }
    }

    fun handleGoogleSuccess(token: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                signInCommander.resolveCommand(
                    SignInCommand.Google(token)
                )
            }.handle(
                onSuccess = {
                    analyticsTracker.track(LoginAnalytics.GoogleLoginSuccess)
                },
                onFinish = {
                    _state.update {
                        it.copy(
                            isGoogleLoading = false
                        )
                    }
                },
                onFailure = { result ->
                    analyticsTracker.track(LoginAnalytics.GoogleLoginFailure)
                    _events.emit(
                        LoginScreenEvents.Failure(
                            message = result.message.orEmpty()
                        )
                    )
                }
            )
        }
    }
}
