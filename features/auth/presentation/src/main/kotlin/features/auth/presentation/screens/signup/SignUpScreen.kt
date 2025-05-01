package features.auth.presentation.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.presentation.R
import features.auth.presentation.screens.login.LoginScreen
import features.auth.presentation.screens.signup.components.PasswordStrengthCard
import features.auth.presentation.screens.signup.components.SignUpForm
import features.auth.presentation.screens.signupfeedback.SignUpFeedbackScreen
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.components.buttons.PrimaryButton
import foundation.designsystem.components.spacer.Spacer
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.Spacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
internal class SignUpScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<SignUpScreenModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()
        val genericErrorMessage = stringResource(R.string.auth_sign_up_error)

        val snackBarState = remember {
            SnackbarHostState()
        }

        foundation.ui.events.EventEffect(viewModel) {
            when (it) {
                SignUpEvents.Success -> navigator?.push(SignUpFeedbackScreen())

                is SignUpEvents.Failure -> {
                    scope.launch {
                        snackBarState.showSnackbar(
                            message = it.message
                        )
                    }
                }

                SignUpEvents.GenericFailure -> scope.launch {
                    snackBarState.showSnackbar(
                        message = genericErrorMessage
                    )
                }
            }
        }

        StateContent(
            onBackClick = { navigator?.pop() },
            onSubmitClick = viewModel::createAccount,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            toggleCensorship = viewModel::toggleCensorship,
            state = state,
            onSignInClick = { navigator?.replaceAll(LoginScreen()) },
            snackBarState = snackBarState
        )
    }

    @Composable
    fun StateContent(
        state: SignUpScreenState,
        snackBarState: SnackbarHostState,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        toggleCensorship: () -> Unit,
        onBackClick: () -> Unit,
        onSubmitClick: () -> Unit,
        onSignInClick: () -> Unit,
    ) {
        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        BackButton(onBackClick = onBackClick)
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(snackBarState)
            },
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(Spacing.medium)
                    .fillMaxSize(),
            ) {
                VerticalSpacer(height = SpacerSize.XLarge3)
                Text(
                    text = stringResource(R.string.auth_sign_up_title),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                VerticalSpacer(height = SpacerSize.Small)
                Text(
                    text = stringResource(R.string.auth_sign_up_description),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                )
                VerticalSpacer(height = SpacerSize.XLarge3)
                SignUpForm(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    onPasswordChange = onPasswordChange,
                    onEmailChange = onEmailChange,
                    toggleCensorship = toggleCensorship
                )
                VerticalSpacer(height = SpacerSize.XLarge)
                PasswordStrengthCard(
                    passwordStrength = state.passwordStrength,
                    modifier = Modifier.fillMaxWidth()
                )
                VerticalSpacer(height = SpacerSize.XLarge)
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.auth_sign_up_submit_button),
                    onClick = onSubmitClick,
                    isEnabled = state.buttonEnabled,
                    isLoading = state.requestLoading
                )
                VerticalSpacer(height = SpacerSize.Medium)
                Spacer(1f)
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSignInClick
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.bodyMedium
                                    .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    .toSpanStyle()
                            ) {
                                append(text = stringResource(R.string.auth_sign_up_have_account_prefix))
                            }
                            append(" ")
                            withStyle(
                                style = MaterialTheme.typography.bodyMedium
                                    .copy(color = MaterialTheme.colorScheme.primary)
                                    .toSpanStyle()
                            ) {
                                append(text = stringResource(R.string.auth_sign_up_have_account_suffix))
                            }
                        }
                    )
                }
            }
        }
    }
}