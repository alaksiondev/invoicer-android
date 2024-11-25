package features.auth.presentation.screens.signup

internal data class SignUpScreenState(
    val email: String = "",
    val confirmEmail: String = "",
    val password: String = "",
    val censored: Boolean = true,
    val requestLoading: Boolean = false
) {
    val emailMatches: Boolean = email == confirmEmail

    val buttonEnabled: Boolean =
        email.isNotBlank() && password.isNotBlank() && emailMatches
}
