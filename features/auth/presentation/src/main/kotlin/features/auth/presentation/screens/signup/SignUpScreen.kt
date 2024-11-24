package features.auth.presentation.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import cafe.adriel.voyager.core.screen.Screen
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

        StateContent(
            onBackClick = { navigator?.pop() },
            onSubmitClick = {}
        )
    }

    @Composable
    fun StateContent(
        onBackClick: () -> Unit,
        onSubmitClick: () -> Unit
    ) {
        Scaffold(
            topBar = {
                MediumTopAppBar(
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
                    .padding(horizontal = Spacing.medium)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.auth_sign_up_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(1f)
                EmailField(
                    value = "",
                    onChange = {}
                )
                PasswordField(
                    value = "",
                    onChange = {},
                    isCensored = true
                )
                VerticalSpacer(height = SpacerSize.Medium)
                Button(
                    onClick = onSubmitClick
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
        }
    )
}

@Composable
private fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    isCensored: Boolean,
    modifier: Modifier = Modifier,
) {
    val trailingIcon = remember {
        derivedStateOf {
            if (isCensored) {
                Icons.Outlined.VisibilityOff
            } else {
                Icons.Outlined.Visibility
            }
        }
    }

    val transformation = remember {
        derivedStateOf {
            if (isCensored) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            }
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
            Icon(
                painter = rememberVectorPainter(
                    image = trailingIcon.value
                ),
                contentDescription = null
            )
        },
        visualTransformation = transformation.value
    )
}