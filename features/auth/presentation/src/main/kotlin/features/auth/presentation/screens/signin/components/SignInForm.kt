package features.auth.presentation.screens.signin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import features.auth.presentation.screens.signin.SignInScreenState
import features.auth.presentation.screens.signup.SignUpScreenState
import foundation.design.system.tokens.Spacing

@Composable
internal fun SignInForm(
    modifier: Modifier = Modifier,
    state: SignInScreenState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    toggleCensorship: () -> Unit,
) {
    val (emailFocus, passwordFocus) = FocusRequester.createRefs()
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        SignInEmailField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocus),
            value = state.email,
            onChange = onEmailChange,
            onImeAction = { passwordFocus.requestFocus() },
            enabled = state.requestLoading.not()
        )
        SignInPasswordField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocus),
            value = state.password,
            onChange = onPasswordChange,
            onImeAction = {
                passwordFocus.freeFocus()
                keyboard?.hide()
            },
            enabled = state.requestLoading.not(),
            isCensored = state.censored,
            toggleCensorship = toggleCensorship
        )
    }
}