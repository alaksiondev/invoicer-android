package io.github.alaksion.invoicer.features.auth.presentation.screens.signupfeedback

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
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.internal.BackHandler
import invoicer.features.auth.presentation.generated.resources.Res
import invoicer.features.auth.presentation.generated.resources.auth_sign_up_feedback_cta
import invoicer.features.auth.presentation.generated.resources.auth_sign_up_feedback_message
import invoicer.features.auth.presentation.generated.resources.auth_sign_up_feedback_title
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreen
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.components.feedback.Feedback
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import org.jetbrains.compose.resources.stringResource

internal class SignUpFeedbackScreen : Screen {

    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val onBack = remember {
            { navigator?.replaceAll(LoginScreen()) }
        }

        BackHandler(true) {
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
                    primaryActionText = stringResource(Res.string.auth_sign_up_feedback_cta),
                    onPrimaryAction = onSubmit,
                    icon = Icons.Outlined.Check,
                    title = stringResource(Res.string.auth_sign_up_feedback_title),
                    description = stringResource(Res.string.auth_sign_up_feedback_message)
                )
            }
        }
    }
}
