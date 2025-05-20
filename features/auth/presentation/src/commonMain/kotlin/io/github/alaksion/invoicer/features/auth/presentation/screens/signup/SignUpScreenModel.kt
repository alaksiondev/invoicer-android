package io.github.alaksion.invoicer.features.auth.presentation.screens.signup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.features.auth.presentation.utils.EmailValidator
import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthValidator
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsTracker
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository
import io.github.alaksion.invoicer.foundation.network.RequestError
import io.github.alaksion.invoicer.foundation.network.request.RequestState
import io.github.alaksion.invoicer.foundation.network.request.launchRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val DuplicateAccountErrorCode = 409

internal class SignUpScreenModel(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher,
    private val emailValidator: EmailValidator,
    private val passwordStrengthValidator: PasswordStrengthValidator,
    private val analyticsTracker: AnalyticsTracker
) : ScreenModel {

    private val _state = MutableStateFlow(SignUpScreenState())
    val state: StateFlow<SignUpScreenState> = _state

    private val _events = MutableSharedFlow<SignUpEvents>()
    val events = _events.asSharedFlow()

    fun onEmailChange(newEmail: String) {
        _state.update { oldState ->
            oldState.copy(
                email = newEmail,
                emailValid = true
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        _state.update {
            it.copy(
                password = newPassword,
                passwordStrength = passwordStrengthValidator.validate(newPassword)
            )
        }
    }

    fun toggleCensorship() {
        _state.update {
            it.copy(
                censored = it.censored.not()
            )
        }
    }

    fun createAccount() {
        if (emailValidator.validate(state.value.email).not()) {
            _state.update { oldState ->
                oldState.copy(
                    emailValid = false
                )
            }
            return
        }

        if (state.value.buttonEnabled) {
            screenModelScope.launch(dispatcher) {
                launchRequest {
                    authRepository.signUp(
                        email = state.value.email,
                        confirmEmail = state.value.email,
                        password = state.value.password
                    )
                }.collect { handleSignUpRequest(it) }
            }
        }
    }

    private suspend fun handleSignUpRequest(
        state: RequestState<Unit>
    ) {
        when (state) {
            is RequestState.Started -> {
                analyticsTracker.track(SignUpAnalytics.Started)
                _state.update {
                    it.copy(
                        requestLoading = true
                    )
                }
            }

            is RequestState.Success -> {
                analyticsTracker.track(SignUpAnalytics.Success)
                _events.emit(SignUpEvents.Success)
            }

            is RequestState.Error -> {
                analyticsTracker.track(SignUpAnalytics.Failure)
                sendErrorEvent(state.exception)
            }

            RequestState.Finished -> _state.update {
                it.copy(
                    requestLoading = false
                )
            }
        }
    }

    private suspend fun sendErrorEvent(error: RequestError) {
        val message = when (error) {
            is RequestError.Http -> {
                if (error.httpCode == DuplicateAccountErrorCode) {
                    SignUpEvents.DuplicateAccount
                } else {
                    error.message?.let { message ->
                        SignUpEvents.Failure(message)
                    } ?: SignUpEvents.GenericFailure
                }
            }

            is RequestError.Other -> SignUpEvents.GenericFailure
        }
        _events.emit(message)
    }
}
