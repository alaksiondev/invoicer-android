package features.auth.presentation.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.spacer.Spacer
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.auth.presentation.R
import features.auth.presentation.screens.signup.components.SignUpConfirmEmailField
import features.auth.presentation.screens.signup.components.SignUpEmailField
import features.auth.presentation.screens.signup.components.SignUpPasswordField
import foundation.design.system.tokens.Spacing

@OptIn(ExperimentalMaterial3Api::class)
internal class SignUpScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<SignUpScreenModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        StateContent(
            onBackClick = { navigator?.pop() },
            onSubmitClick = viewModel::createAccount,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            toggleCensorship = viewModel::toggleCensorship,
            onConfirmEmail = viewModel::onConfirmEmailChange,
            onCheckValidEmail = viewModel::checkEmailValid,
            state = state,
        )
    }

    @Composable
    fun StateContent(
        state: SignUpScreenState,
        onEmailChange: (String) -> Unit,
        onConfirmEmail: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onCheckValidEmail: () -> Unit,
        toggleCensorship: () -> Unit,
        onBackClick: () -> Unit,
        onSubmitClick: () -> Unit
    ) {
        val (emailFocus, confirmEmailFocus, passwordFocus) = FocusRequester.createRefs()
        val keyboard = LocalSoftwareKeyboardController.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClick
                        ) {
                            Icon(
                                painter = rememberVectorPainter(
                                    image = Icons.AutoMirrored.Outlined.ArrowBack
                                ),
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(Spacing.medium)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.auth_sign_up_title),
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(1f)
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
                VerticalSpacer(height = SpacerSize.Medium)
                SignUpConfirmEmailField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(confirmEmailFocus),
                    value = state.confirmEmail,
                    onChange = onConfirmEmail,
                    emailMatches = state.emailMatches,
                    onImeAction = { passwordFocus.requestFocus() }
                )
                VerticalSpacer(height = SpacerSize.Medium)
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
                Spacer(1f)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSubmitClick,
                    enabled = state.buttonEnabled
                ) {
                    Text(stringResource(R.string.auth_sign_up_submit_button))
                }
            }
        }
    }
}