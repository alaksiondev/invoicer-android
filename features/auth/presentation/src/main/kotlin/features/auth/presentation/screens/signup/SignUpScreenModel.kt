package features.auth.presentation.screens.signup

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.auth.domain.usecase.SignUpUseCase
import foundation.network.request.RequestState
import foundation.network.request.launchRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SignUpScreenModel(
    private val signUpUseCase: SignUpUseCase,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel {

    private val _state = MutableStateFlow(SignUpScreenState())
    val state: StateFlow<SignUpScreenState> = _state

    fun onEmailChange(newEmail: String) {
        _state.update {
            it.copy(
                email = newEmail
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
                    signUpUseCase.invoke(
                        email = state.value.email,
                        confirmEmail = state.value.confirmEmail,
                        password = state.value.password
                    )
                }.collect { handleSignUpRequest(it) }
            }
        }
    }

    private fun handleSignUpRequest(
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
                Log.d("sign-up-result", "success")
            }

            is RequestState.Error -> {
                Log.d("sign-up-result", "failed")
            }

            RequestState.Finished -> _state.update {
                it.copy(
                    requestLoading = false
                )
            }
        }
    }
}