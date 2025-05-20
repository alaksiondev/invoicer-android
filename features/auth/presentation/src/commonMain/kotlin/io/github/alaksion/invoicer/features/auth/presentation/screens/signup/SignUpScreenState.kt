package io.github.alaksion.invoicer.features.auth.presentation.screens.signup

import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthResult

internal data class SignUpScreenState(
    val email: String = "",
    val password: String = "",
    val censored: Boolean = true,
    val requestLoading: Boolean = false,
    val emailValid: Boolean = true,
    val passwordStrength: PasswordStrengthResult = PasswordStrengthResult(
        lengthValid = false,
        upperCaseValid = false,
        lowerCaseValid = false,
        digitValid = false,
        specialCharacterValid = false,
    ),
) {

    val buttonEnabled: Boolean =
        email.isNotBlank() && password.isNotBlank() && emailValid && requestLoading.not()
}

internal sealed interface SignUpEvents {
    data object Success : SignUpEvents
    data object GenericFailure : SignUpEvents
    data class Failure(val message: String) : SignUpEvents
    data object DuplicateAccount : SignUpEvents
}
