package features.auth.presentation.utils

interface PasswordStrengthValidator {
    fun validate(password: String): PasswordStrengthResult
}

data class PasswordStrengthResult(
    val lengthValid: Boolean,
    val upperCaseValid: Boolean,
    val lowerCaseValid: Boolean,
    val digitValid: Boolean,
    val specialCharacterValid: Boolean,
)

internal object PasswordStrengthValidatorImpl : PasswordStrengthValidator {

    override fun validate(password: String): PasswordStrengthResult {
        var lengthValid = password.length >= 8
        var upperCaseValid = false
        var lowerCaseValid = false
        var digitValid = false
        var specialCharacterValid = false

        password.forEach {
            when {
                it.isUpperCase() -> upperCaseValid = true
                it.isLowerCase() -> lowerCaseValid = true
                it.isDigit() -> digitValid = true
                !it.isLetterOrDigit() -> specialCharacterValid = true
            }
        }

        return PasswordStrengthResult(
            lengthValid = lengthValid,
            upperCaseValid = upperCaseValid,
            lowerCaseValid = lowerCaseValid,
            digitValid = digitValid,
            specialCharacterValid = specialCharacterValid,
        )
    }
}