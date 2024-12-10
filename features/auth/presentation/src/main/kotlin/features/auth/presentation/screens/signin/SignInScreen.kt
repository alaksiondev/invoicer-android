package features.auth.presentation.screens.signin

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
import features.auth.design.system.components.buttons.BackButton
import features.auth.design.system.components.spacer.Spacer
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.auth.presentation.R
import features.auth.presentation.screens.signin.components.SignInCta
import features.auth.presentation.screens.signin.components.SignInForm
import features.auth.presentation.screens.signup.SignUpScreen
import foundation.design.system.tokens.Spacing
import foundation.events.EventEffect
import kotlinx.coroutines.launch

internal class SignInScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<SignInScreenModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()
        val snackBarHost = remember { SnackbarHostState() }
        val genericErrorMessage = stringResource(R.string.auth_sign_in_error)

        StateContent(
            state = state,
            callBacks = rememberSignInCallbacks(
                onSubmit = viewModel::submit,
                onEmailChanged = viewModel::onEmailChanged,
                onPasswordChanged = viewModel::onPasswordChanged,
                onToggleCensorship = viewModel::toggleCensorship,
                onBack = { navigator?.pop() },
                onSignUpClick = { navigator?.push(SignUpScreen()) },
            ),
            snackbarHostState = snackBarHost
        )

        EventEffect(viewModel) {
            when (it) {
                is SignInEvents.Failure -> {
                    scope.launch {
                        snackBarHost.showSnackbar(
                            message = it.message
                        )
                    }
                }

                SignInEvents.GenericFailure -> scope.launch {
                    snackBarHost.showSnackbar(
                        message = genericErrorMessage
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: SignInScreenState,
        snackbarHostState: SnackbarHostState,
        callBacks: SignInCallBacks
    ) {
        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        BackButton(onBackClick = callBacks.onBack)
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(Spacing.medium)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.auth_sign_in_title),
                    style = MaterialTheme.typography.headlineLarge
                )
                VerticalSpacer(height = SpacerSize.Medium)
                SignInForm(
                    state = state,
                    onPasswordChange = callBacks.onPasswordChanged,
                    onEmailChange = callBacks.onEmailChanged,
                    toggleCensorship = callBacks.toggleCensorship
                )
                Spacer(1f)
                SignInCta(
                    onClick = callBacks.onSubmit,
                    onSignUpClick = callBacks.onSignUpClick,
                    enabled = state.buttonEnabled,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}