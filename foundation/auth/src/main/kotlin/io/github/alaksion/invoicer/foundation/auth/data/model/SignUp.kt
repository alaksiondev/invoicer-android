package io.github.alaksion.invoicer.foundation.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpRequest(
    val email: String,
    val confirmEmail: String,
    val password: String,
)