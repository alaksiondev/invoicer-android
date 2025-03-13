package features.auth.presentation.screens.signup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.auth.domain.repository.AuthRepository
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import foundation.exception.RequestError
import foundation.network.request.RequestState
import foundation.network.request.launchRequest
import foundation.validator.impl.EmailValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SignUpScreenModel(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher,
    private val emailValidator: EmailValidator,
) : ScreenModel, foundation.ui.events.EventAware<SignUpEvents> by foundation.ui.events.EventPublisher() {

    private val _state = MutableStateFlow(SignUpScreenState())
    val state: StateFlow<SignUpScreenState> = _state

    fun onEmailChange(newEmail: String) {
        val newEmailValid = emailValidator.validate(newEmail)
        _state.update { oldState ->
            oldState.copy(
                email = newEmail,
                emailValid = newEmailValid || oldState.emailValid
            )
        }
    }

    fun checkEmailValid() {
        _state.update { oldState ->
            oldState.copy(
                emailValid = emailValidator.validate(oldState.email)
            )
        }
    }

    fun onConfirmEmailChange(newConfirmEmail: String) {
        _state.update {
            it.copy(
                confirmEmail = newConfirmEmail
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        _state.update {
            it.copy(
                password = newPassword
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
        if (state.value.buttonEnabled) {
            screenModelScope.launch(dispatcher) {
                launchRequest {
                    authRepository.signUp(
                        email = state.value.email,
                        confirmEmail = state.value.confirmEmail,
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
                _state.update {
                    it.copy(
                        requestLoading = true
                    )
                }
            }

            is RequestState.Success -> {
                publish(SignUpEvents.Success)
            }

            is RequestState.Error -> {
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
            is RequestError.Http -> error.message?.let {
                SignUpEvents.Failure(it)
            } ?: SignUpEvents.GenericFailure

            is RequestError.Other -> SignUpEvents.GenericFailure
        }
        publish(message)
    }
}