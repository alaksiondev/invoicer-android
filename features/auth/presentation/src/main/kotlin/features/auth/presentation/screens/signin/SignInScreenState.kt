package features.auth.presentation.screens.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class SignInScreenState(
    val email: String = "",
    val password: String = "",
    val censored: Boolean = true,
    val requestLoading: Boolean = false,
) {
    val buttonEnabled: Boolean =
        email.isNotBlank() && password.isNotBlank() && requestLoading.not()
}

internal data class SignInCallBacks(
    val onEmailChanged: (String) -> Unit,
    val onPasswordChanged: (String) -> Unit,
    val onSubmit: () -> Unit,
    val toggleCensorship: () -> Unit,
    val onBack: () -> Unit,
)

@Composable
internal fun rememberSignInCallbacks(
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onToggleCensorship: () -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) = remember {
    SignInCallBacks(
        onEmailChanged = onEmailChanged,
        onPasswordChanged = onPasswordChanged,
        onSubmit = onSubmit,
        toggleCensorship = onToggleCensorship,
        onBack = onBack
    )
}
