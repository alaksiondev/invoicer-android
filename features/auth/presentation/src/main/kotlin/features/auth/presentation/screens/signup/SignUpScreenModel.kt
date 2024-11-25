package features.auth.presentation.screens.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel

internal class SignUpScreenModel : ScreenModel {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _censored = mutableStateOf(true)
    val censored: State<Boolean> = _censored

    val buttonEnabled = derivedStateOf {
        email.value.isNotBlank() && password.value.isNotBlank()
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun toggleCensorship() {
        _censored.value = !_censored.value
    }
}