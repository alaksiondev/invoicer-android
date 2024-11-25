package features.auth.presentation.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.spacer.Spacer
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.auth.presentation.R
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
            onSubmitClick = {},
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            toggleCensorship = viewModel::toggleCensorship,
            onConfirmEmail = viewModel::onConfirmEmailChange,
            state = state,
        )
    }

    @Composable
    fun StateContent(
        state: SignUpScreenState,
        onEmailChange: (String) -> Unit,
        onConfirmEmail: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
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
                EmailField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(emailFocus),
                    value = state.email,
                    onChange = onEmailChange,
                    onImeAction = { confirmEmailFocus.requestFocus() }
                )
                VerticalSpacer(height = SpacerSize.Medium)
                ConfirmEmailField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(confirmEmailFocus),
                    value = state.confirmEmail,
                    onChange = onConfirmEmail,
                    emailMatches = state.emailMatches,
                    onImeAction = { passwordFocus.requestFocus() }
                )
                VerticalSpacer(height = SpacerSize.Medium)
                PasswordField(
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

@Composable
private fun EmailField(
    value: String,
    onChange: (String) -> Unit,
    onImeAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = rememberVectorPainter(
                    image = Icons.Outlined.Email
                ),
                contentDescription = null
            )
        },
        label = {
            Text(stringResource(R.string.auth_sign_up_email_label))
        },
        placeholder = {
            Text(stringResource(R.string.auth_sign_up_email_placeholder))
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() }
        )
    )
}

@Composable
private fun ConfirmEmailField(
    value: String,
    emailMatches: Boolean,
    onChange: (String) -> Unit,
    onImeAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = rememberVectorPainter(
                    image = Icons.Outlined.Email
                ),
                contentDescription = null
            )
        },
        label = {
            Text(stringResource(R.string.auth_sign_up_email_label))
        },
        placeholder = {
            Text(stringResource(R.string.auth_sign_up_email_placeholder))
        },
        isError = emailMatches.not(),
        supportingText = emailMatches.takeIf { it.not() }?.let {
            {
                Text(
                    text = stringResource(R.string.auth_sign_up_email_match_error),
                )
            }
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() }
        )
    )
}


@Composable
private fun PasswordField(
    value: String,
    isCensored: Boolean,
    onChange: (String) -> Unit,
    toggleCensorship: () -> Unit,
    onImeAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val trailingIcon = remember(isCensored) {
        if (isCensored) {
            Icons.Outlined.Visibility
        } else {
            Icons.Outlined.VisibilityOff
        }
    }

    val transformation = remember(isCensored) {

        if (isCensored) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = rememberVectorPainter(
                    image = Icons.Outlined.Password
                ),
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(
                onClick = toggleCensorship
            ) {
                Icon(
                    painter = rememberVectorPainter(
                        image = trailingIcon
                    ),
                    contentDescription = null
                )
            }
        },
        visualTransformation = transformation,
        label = {
            Text(stringResource(R.string.auth_sign_up_password_label))
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() }
        )
    )
}