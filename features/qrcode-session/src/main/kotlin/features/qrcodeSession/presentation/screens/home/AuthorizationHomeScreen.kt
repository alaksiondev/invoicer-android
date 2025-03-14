package features.qrcodeSession.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import feature.qrcodeSession.R
import features.qrcodeSession.presentation.screens.scan.AuthorizationScanScreen
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.tokens.Spacing

internal class AuthorizationHomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        StateContent(
            onOpenCodeScanner = { navigator?.push(AuthorizationScanScreen()) },
            onBackPressed = { navigator?.pop() }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        onOpenCodeScanner: () -> Unit,
        onBackPressed: () -> Unit,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.qrcode_session_home_title)) },
                    navigationIcon = {
                        BackButton(onBackClick = onBackPressed)
                    }
                )
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(Spacing.medium)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onOpenCodeScanner
                ) {
                    Text(stringResource(R.string.qrcode_session_home_authorize_cta))
                }
            }
        }
    }
}