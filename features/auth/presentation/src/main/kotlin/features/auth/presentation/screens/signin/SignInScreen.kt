package features.auth.presentation.screens.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.buttons.BackButton
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.auth.presentation.R
import foundation.design.system.tokens.Spacing

internal class SignInScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<SignInScreenModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        StateContent(
            state = state,
            callBacks = rememberSignInCallbacks(
                onSubmit = viewModel::submit,
                onEmailChanged = viewModel::onEmailChanged,
                onPasswordChanged = viewModel::onPasswordChanged,
                onToggleCensorship = viewModel::toggleCensorship,
                onBack = { navigator?.pop() }
            )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: SignInScreenState,
        callBacks: SignInCallBacks
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        BackButton(onBackClick = callBacks.onBack)
                    }
                )
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
            }
        }
    }
}