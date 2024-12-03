package features.auth.presentation.screens.signin

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class SignInScreenModel : ScreenModel {
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

    fun submit() = Unit
}