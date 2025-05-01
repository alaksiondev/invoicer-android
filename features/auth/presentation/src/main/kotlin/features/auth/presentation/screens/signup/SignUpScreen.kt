package features.auth.presentation.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.presentation.R
import features.auth.presentation.screens.login.LoginScreen
import features.auth.presentation.screens.signup.components.SignUpCta
import features.auth.presentation.screens.signup.components.SignUpForm
import features.auth.presentation.screens.signupfeedback.SignUpFeedbackScreen
import foundation.designsystem.components.buttons.BackButton
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
            onConfirmEmail = viewModel::onConfirmEmailChange,
            onCheckValidEmail = viewModel::checkEmailValid,
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
        onConfirmEmail: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onCheckValidEmail: () -> Unit,
        toggleCensorship: () -> Unit,
        onBackClick: () -> Unit,
        onSubmitClick: () -> Unit,
        onSignInClick: () -> Unit,
    ) {
        val scrollState = rememberScrollState()

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
                Text(
                    text = stringResource(R.string.auth_sign_up_title),
                    style = MaterialTheme.typography.headlineLarge
                )
                VerticalSpacer(height = SpacerSize.Medium)
                SignUpForm(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState),
                    state = state,
                    onConfirmEmail = onConfirmEmail,
                    onCheckValidEmail = onCheckValidEmail,
                    onPasswordChange = onPasswordChange,
                    onEmailChange = onEmailChange,
                    toggleCensorship = toggleCensorship
                )
                SignUpCta(
                    onClick = onSubmitClick,
                    enabled = state.buttonEnabled,
                    modifier = Modifier.fillMaxWidth(),
                    onSignInClick = onSignInClick
                )
            }
        }
    }
}