package features.auth.presentation.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val censored: Boolean = true,
    val isSignInLoading: Boolean = false,
    val isGoogleLoading: Boolean = false,
) {
    val buttonEnabled: Boolean =
        email.isNotBlank() && password.isNotBlank() && isSignInLoading.not() && isGoogleLoading.not()

    val googleEnabled: Boolean =
        isSignInLoading.not() && isGoogleLoading.not()
}

internal data class LoginScreenCallbacks(
    val onEmailChanged: (String) -> Unit,
    val onPasswordChanged: (String) -> Unit,
    val onSubmit: () -> Unit,
    val toggleCensorship: () -> Unit,
    val onBack: () -> Unit,
    val onSignUpClick: () -> Unit,
    val onLaunchGoogle: () -> Unit
)

internal sealed interface LoginScreenEvents {
    data object GenericFailure : LoginScreenEvents
    data class Failure(val message: String) : LoginScreenEvents
}

@Composable
internal fun rememberLoginCallbacks(
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onToggleCensorship: () -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    onSignUpClick: () -> Unit,
    onLaunchGoogle: () -> Unit
) = remember {
    LoginScreenCallbacks(
        onEmailChanged = onEmailChanged,
        onPasswordChanged = onPasswordChanged,
        onSubmit = onSubmit,
        toggleCensorship = onToggleCensorship,
        onBack = onBack,
        onSignUpClick = onSignUpClick,
        onLaunchGoogle = onLaunchGoogle
    )
}
