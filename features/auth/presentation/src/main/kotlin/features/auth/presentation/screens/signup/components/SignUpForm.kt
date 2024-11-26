package features.auth.presentation.screens.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import features.auth.presentation.screens.signup.SignUpScreenState
import foundation.design.system.tokens.Spacing

@Composable
internal fun SignUpForm(
    modifier: Modifier = Modifier,
    state: SignUpScreenState,
    onEmailChange: (String) -> Unit,
    onConfirmEmail: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCheckValidEmail: () -> Unit,
    toggleCensorship: () -> Unit,
) {
    val (emailFocus, confirmEmailFocus, passwordFocus) = FocusRequester.createRefs()
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {
        SignUpEmailField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocus)
                .onFocusChanged { state ->
                    if (state.hasFocus.not()) {
                        onCheckValidEmail()
                    }
                },
            value = state.email,
            onChange = onEmailChange,
            onImeAction = { confirmEmailFocus.requestFocus() },
            isEmailValid = state.emailValid
        )
        SignUpConfirmEmailField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(confirmEmailFocus),
            value = state.confirmEmail,
            onChange = onConfirmEmail,
            emailMatches = state.emailMatches,
            onImeAction = { passwordFocus.requestFocus() }
        )
        SignUpPasswordField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocus),
            value = state.password,
            onChange = onPasswordChange,
            isCensored = state.censored,
            toggleCensorship = toggleCensorship,
            onImeAction = { keyboard?.hide() }
        )
        PasswordStrength(
            modifier = Modifier.fillMaxWidth()
        )
    }
}