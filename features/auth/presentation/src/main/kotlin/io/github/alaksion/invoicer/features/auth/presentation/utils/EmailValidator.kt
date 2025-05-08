package io.github.alaksion.invoicer.features.auth.presentation.utils

interface EmailValidator {
    fun validate(email: String): Boolean
}

internal class EmailValidatorImpl : EmailValidator {
    override fun validate(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
}
