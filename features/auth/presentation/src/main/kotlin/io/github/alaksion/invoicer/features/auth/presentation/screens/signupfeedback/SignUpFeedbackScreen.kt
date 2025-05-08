package io.github.alaksion.invoicer.features.auth.presentation.screens.signupfeedback

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.components.feedback.Feedback
import foundation.designsystem.components.preview.ThemeContainer
import foundation.designsystem.tokens.Spacing
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreen
import io.github.alaksion.invoicer.features.auth.presentation.R

internal class SignUpFeedbackScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val onBack = remember {
            { navigator?.replaceAll(LoginScreen()) }
        }

        BackHandler {
            onBack()
        }

        StateContent(
            onSubmit = { onBack() },
            onBack = { onBack() }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        onSubmit: () -> Unit,
        onBack: () -> Unit,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        BackButton(
                            onBackClick = onBack,
                            icon = Icons.Outlined.Close
                        )
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(Spacing.medium)
            ) {
                Feedback(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(),
                    primaryActionText = stringResource(R.string.auth_sign_up_feedback_cta),
                    onPrimaryAction = onSubmit,
                    icon = Icons.Outlined.Check,
                    title = stringResource(R.string.auth_sign_up_feedback_title),
                    description = stringResource(R.string.auth_sign_up_feedback_message)
                )
            }

        }
    }
}

@Composable
@Preview
private fun Preview() {
    ThemeContainer {
        SignUpFeedbackScreen().StateContent(onBack = {}, onSubmit = {})
    }
}