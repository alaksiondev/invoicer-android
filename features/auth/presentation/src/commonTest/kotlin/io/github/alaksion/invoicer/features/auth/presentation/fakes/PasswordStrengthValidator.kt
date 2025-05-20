package io.github.alaksion.invoicer.features.auth.presentation.fakes

import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthResult
import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthValidator

class FakePasswordStrengthValidator: PasswordStrengthValidator {

    var response = PasswordStrengthResult(
        lengthValid = true,
        upperCaseValid = true,
        lowerCaseValid = true,
        digitValid = true,
        specialCharacterValid = true
    )

    override fun validate(password: String): PasswordStrengthResult {
        return response
    }
}