package features.auth.presentation.screens.signup

internal data class SignUpScreenState(
    val email: String = "",
    val password: String = "",
    val censored: Boolean = true,
    val requestLoading: Boolean = false,
    val emailValid: Boolean = true,
) {

    val buttonEnabled: Boolean =
        email.isNotBlank() && password.isNotBlank() && emailValid && requestLoading.not()
}

internal sealed interface SignUpEvents {
    data object Success : SignUpEvents
    data object GenericFailure : SignUpEvents
    data class Failure(val message: String) : SignUpEvents
}
