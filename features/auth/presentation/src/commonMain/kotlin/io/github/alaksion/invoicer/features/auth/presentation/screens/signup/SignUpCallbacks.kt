package io.github.alaksion.invoicer.features.auth.presentation.screens.signup

internal data class SignUpCallbacks(
    val onEmailChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val toggleCensorship: () -> Unit,
    val onBackClick: () -> Unit,
    val onSubmitClick: () -> Unit,
    val onSignInClick: () -> Unit,
    val onDismissDialog: () -> Unit,
)
