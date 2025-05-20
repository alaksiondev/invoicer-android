package io.github.alaksion.invoicer.features.auth.presentation.fakes

import io.github.alaksion.invoicer.features.auth.presentation.utils.EmailValidator

class FakeEmailValidator : EmailValidator {

    var response = true

    override fun validate(email: String): Boolean {
        return response
    }
}