package features.auth.presentation.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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

        StateContent(
            onBackClick = { navigator?.pop() },
            onSubmitClick = {},
            email = viewModel.email.value,
            password = viewModel.password.value,
            isCensored = viewModel.censored.value,
            buttonEnabled = viewModel.buttonEnabled.value,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            toggleCensorship = viewModel::toggleCensorship
        )
    }

    @Composable
    fun StateContent(
        email: String,
        password: String,
        isCensored: Boolean,
        buttonEnabled: Boolean,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        toggleCensorship: () -> Unit,
        onBackClick: () -> Unit,
        onSubmitClick: () -> Unit
    ) {
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
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onChange = onEmailChange
                )
                VerticalSpacer(height = SpacerSize.Medium)
                PasswordField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onChange = onPasswordChange,
                    isCensored = isCensored,
                    toggleCensorship = toggleCensorship
                )
                Spacer(1f)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSubmitClick,
                    enabled = buttonEnabled
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
        }
    )
}

@Composable
private fun PasswordField(
    value: String,
    isCensored: Boolean,
    onChange: (String) -> Unit,
    toggleCensorship: () -> Unit,
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
        }
    )
}