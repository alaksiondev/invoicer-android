package features.auth.presentation.screens.signup

import cafe.adriel.voyager.core.model.ScreenModel
import features.auth.domain.usecase.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

internal class SignUpScreenModel(
    private val signUpUseCase: SignUpUseCase
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
            runCatching {

            }
        }
    }
}