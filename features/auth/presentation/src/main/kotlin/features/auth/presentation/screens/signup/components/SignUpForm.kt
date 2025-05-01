package features.auth.presentation.screens.signup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component3
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import features.auth.presentation.screens.signup.SignUpScreenState
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer

@Composable
internal fun SignUpForm(
    modifier: Modifier = Modifier,
    state: SignUpScreenState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    toggleCensorship: () -> Unit,
) {
    val (emailFocus, confirmEmailFocus, passwordFocus) = FocusRequester.createRefs()
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier,
    ) {
        SignUpEmailField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocus),
            value = state.email,
            onChange = onEmailChange,
            onImeAction = { confirmEmailFocus.requestFocus() },
            isEmailValid = state.emailValid,
            enabled = state.requestLoading.not()
        )

        VerticalSpacer(SpacerSize.Medium)

        SignUpPasswordField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocus),
            value = state.password,
            onChange = onPasswordChange,
            isCensored = state.censored,
            toggleCensorship = toggleCensorship,
            onImeAction = { keyboard?.hide() },
            enabled = state.requestLoading.not()
        )
    }
}