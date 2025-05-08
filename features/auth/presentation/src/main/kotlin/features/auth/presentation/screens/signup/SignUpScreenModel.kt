package features.auth.presentation.screens.signup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.auth.presentation.utils.EmailValidator
import features.auth.presentation.utils.PasswordStrengthValidator
import foundation.network.RequestError
import foundation.network.request.RequestState
import foundation.network.request.launchRequest
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsTracker
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SignUpScreenModel(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher,
    private val emailValidator: EmailValidator,
    private val passwordStrengthValidator: PasswordStrengthValidator,
    private val analyticsTracker: AnalyticsTracker
) : ScreenModel,
    foundation.ui.events.EventAware<SignUpEvents> by foundation.ui.events.EventPublisher() {

    private val _state = MutableStateFlow(SignUpScreenState())
    val state: StateFlow<SignUpScreenState> = _state

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
                publish(SignUpEvents.Success)
            }

            is RequestState.Error -> {
                analyticsTracker.track(SignUpAnalytics.Failure)
                when (val error = state.exception) {
                    is RequestError.Http -> {
                        if (error.httpCode == 409) {
                            publish(SignUpEvents.DuplicateAccount)
                        }
                    }

                    else -> sendErrorEvent(error)
                }
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
            is RequestError.Http -> error.message?.let {
                SignUpEvents.Failure(it)
            } ?: SignUpEvents.GenericFailure

            is RequestError.Other -> SignUpEvents.GenericFailure
        }
        publish(message)
    }
}