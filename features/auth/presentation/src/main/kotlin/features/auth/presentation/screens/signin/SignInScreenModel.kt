package features.auth.presentation.screens.signin

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.auth.domain.repository.AuthRepository
import foundation.auth.watchers.AuthEvent
import foundation.auth.watchers.AuthEventPublisher
import foundation.network.RequestError
import foundation.network.request.handle
import foundation.network.request.launchRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SignInScreenModel(
    private val authRepository: AuthRepository,
    private val authEventPublisher: AuthEventPublisher
) : ScreenModel, foundation.ui.events.EventAware<SignInEvents> by foundation.ui.events.EventPublisher() {
    private val _state = MutableStateFlow(SignInScreenState())
    val state: StateFlow<SignInScreenState> = _state

    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun toggleCensorship() {
        _state.value = _state.value.copy(censored = !_state.value.censored)
    }

    fun submit() {
        if (_state.value.buttonEnabled) handleSignInRequest()
    }

    private fun handleSignInRequest() {
        screenModelScope.launch {
            launchRequest {
                authRepository.signIn(
                    email = _state.value.email,
                    password = _state.value.password
                )
            }.handle(
                onStart = { _state.update { it.copy(requestLoading = true) } },
                onFinish = { _state.update { it.copy(requestLoading = false) } },
                onFailure = {
                    sendErrorEvent(it)
                },
                onSuccess = { authEventPublisher.publish(event = AuthEvent.SignIn) }
            )
        }
    }

    private suspend fun sendErrorEvent(error: RequestError) {
        val message = when (error) {
            is RequestError.Http -> error.message?.let {
                SignInEvents.Failure(it)
            } ?: SignInEvents.GenericFailure

            is RequestError.Other -> SignInEvents.GenericFailure
        }
        publish(message)
    }
}