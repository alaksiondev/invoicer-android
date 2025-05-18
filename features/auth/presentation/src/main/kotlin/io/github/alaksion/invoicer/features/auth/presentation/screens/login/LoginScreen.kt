package io.github.alaksion.invoicer.features.auth.presentation.screens.login

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import io.github.alaksion.invoicer.foundation.navigation.extensions.pushToFront
import invoicer.foundation.design_system.generated.resources.Res
import invoicer.foundation.design_system.generated.resources.google
import io.github.alaksion.invoicer.features.auth.presentation.R
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.components.SignInForm
import io.github.alaksion.invoicer.features.auth.presentation.screens.signup.SignUpScreen
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.TextDivider
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.SecondaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.Spacer
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

internal class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<LoginScreenModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()
        val snackBarHost = remember { SnackbarHostState() }
        val genericErrorMessage = stringResource(R.string.auth_sign_in_error)
        val keyboard = LocalSoftwareKeyboardController.current

        val firebaseLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                viewModel.handleGoogleTask(task)
            } else {
                viewModel.cancelGoogleSignIn()
            }
        }

        StateContent(
            state = state,
            callBacks = rememberLoginCallbacks(
                onSubmit = {
                    keyboard?.hide()
                    viewModel.submit()
                },
                onEmailChanged = viewModel::onEmailChanged,
                onPasswordChanged = viewModel::onPasswordChanged,
                onToggleCensorship = viewModel::toggleCensorship,
                onBack = { navigator?.pop() },
                onSignUpClick = {
                    navigator?.pushToFront(SignUpScreen())
                },
                onLaunchGoogle = {
                    firebaseLauncher.launch(viewModel.getGoogleClient().signInIntent)
                }
            ),
            snackbarHostState = snackBarHost
        )

        LaunchedEffect(viewModel.events) {
            viewModel.events.collectLatest {
                when (it) {
                    is LoginScreenEvents.Failure -> {
                        scope.launch {
                            snackBarHost.showSnackbar(
                                message = it.message
                            )
                        }
                    }

                    LoginScreenEvents.GenericFailure -> scope.launch {
                        snackBarHost.showSnackbar(
                            message = genericErrorMessage
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: LoginScreenState,
        snackbarHostState: SnackbarHostState,
        callBacks: LoginScreenCallbacks
    ) {
        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    title = {
                        Text("App Logo here")
                    },
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(Spacing.medium)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                VerticalSpacer(height = SpacerSize.XLarge3)
                ScreenTitle(
                    title = stringResource(R.string.auth_sign_in_title),
                    subTitle = stringResource(R.string.auth_sign_in_description)
                )
                VerticalSpacer(height = SpacerSize.XLarge3)
                SignInForm(
                    state = state,
                    onPasswordChange = callBacks.onPasswordChanged,
                    onEmailChange = callBacks.onEmailChanged,
                    toggleCensorship = callBacks.toggleCensorship
                )
                VerticalSpacer(height = SpacerSize.XLarge)
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = callBacks.onSubmit,
                    isEnabled = state.buttonEnabled,
                    isLoading = state.isSignInLoading,
                    label = stringResource(R.string.auth_sign_in_submit_button)
                )
                VerticalSpacer(height = SpacerSize.XLarge)
                TextDivider(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.auth_sign_in_text_divider)
                )
                VerticalSpacer(height = SpacerSize.Medium)
                SecondaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.auth_sign_in_google_button),
                    onClick = callBacks.onLaunchGoogle,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.google),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.White, CircleShape)
                                .padding(2.dp)
                        )
                    },
                    isLoading = state.isGoogleLoading,
                    isEnabled = state.googleEnabled
                )
                VerticalSpacer(height = SpacerSize.Medium)
                Spacer(1f)
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = callBacks.onSignUpClick
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.bodyMedium
                                    .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    .toSpanStyle()
                            ) {
                                append(text = stringResource(R.string.auth_sign_in_dont_have_account_prefix))
                            }
                            append(" ")
                            withStyle(
                                style = MaterialTheme.typography.bodyMedium
                                    .copy(color = MaterialTheme.colorScheme.primary)
                                    .toSpanStyle()
                            ) {
                                append(text = stringResource(R.string.auth_sign_in_dont_have_account_suffix))
                            }
                        }
                    )
                }
            }
        }
    }
}
