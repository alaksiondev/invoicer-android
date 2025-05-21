package io.github.alaksion.invoicer.features.qrcodeSession.presentation.screens.home

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import invoicer.features.qrcode_session.generated.resources.Res
import invoicer.features.qrcode_session.generated.resources.qrcode_session_home_authorize_cta
import invoicer.features.qrcode_session.generated.resources.qrcode_session_home_title
import io.github.alaksion.invoicer.features.qrcodeSession.presentation.screens.scan.AuthorizationScanScreen
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import org.jetbrains.compose.resources.stringResource

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
                    title = { Text(stringResource(Res.string.qrcode_session_home_title)) },
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
                    Text(stringResource(Res.string.qrcode_session_home_authorize_cta))
                }
            }
        }
    }
}
