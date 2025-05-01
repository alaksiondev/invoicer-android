package features.auth.presentation.screens.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class SignInScreenState(
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

internal data class SignInCallBacks(
    val onEmailChanged: (String) -> Unit,
    val onPasswordChanged: (String) -> Unit,
    val onSubmit: () -> Unit,
    val toggleCensorship: () -> Unit,
    val onBack: () -> Unit,
    val onSignUpClick: () -> Unit,
    val onLaunchGoogle: () -> Unit
)

internal sealed interface SignInEvents {
    data object GenericFailure : SignInEvents
    data class Failure(val message: String) : SignInEvents
}

@Composable
internal fun rememberSignInCallbacks(
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onToggleCensorship: () -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    onSignUpClick: () -> Unit,
    onLaunchGoogle: () -> Unit
) = remember {
    SignInCallBacks(
        onEmailChanged = onEmailChanged,
        onPasswordChanged = onPasswordChanged,
        onSubmit = onSubmit,
        toggleCensorship = onToggleCensorship,
        onBack = onBack,
        onSignUpClick = onSignUpClick,
        onLaunchGoogle = onLaunchGoogle
    )
}
