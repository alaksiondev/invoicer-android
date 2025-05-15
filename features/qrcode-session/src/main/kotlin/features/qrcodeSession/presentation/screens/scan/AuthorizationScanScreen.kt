package features.qrcodeSession.presentation.screens.scan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import feature.qrcodeSession.R
import features.qrcodeSession.presentation.barcodeanalyzer.QrCodeAnalyzer
import features.qrcodeSession.presentation.barcodeanalyzer.rememberQrCodeAnalyzer
import features.qrcodeSession.presentation.screens.confirmation.AuthorizationConfirmationScreen
import features.qrcodeSession.presentation.screens.scan.components.CameraView
import io.github.alaksion.invoicer.foundation.ui.events.EventEffect
import io.github.alaksion.invoicer.foundation.designSystem.components.LoadingState
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import kotlinx.coroutines.launch

internal class AuthorizationScanScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<AuthorizationScanScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val snackBarHost = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.current

        val analyzer = rememberQrCodeAnalyzer(
            onSuccess = screenModel::onScanSuccess,
            onError = { screenModel.onScanError() }
        )

        StateContent(
            qrCodeAnalyzer = analyzer,
            state = state,
            snackBarHostState = snackBarHost,
            onBackButton = { navigator?.pop() }
        )

        EventEffect(screenModel) { event ->
            when (event) {
                AuthorizationScanEvents.InvalidCode, AuthorizationScanEvents.CodeNotFound ->
                    scope.launch {
                        snackBarHost.showSnackbar(message = "Invalid QrCode")
                    }

                is AuthorizationScanEvents.ProceedToConfirmation -> navigator?.push(
                    AuthorizationConfirmationScreen(
                        codeContentId = event.contentId
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        qrCodeAnalyzer: QrCodeAnalyzer,
        state: AuthorizationScanState,
        snackBarHostState: SnackbarHostState,
        onBackButton: () -> Unit
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackBarHostState)
            },
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.qrcode_scan_title)) },
                    navigationIcon = { BackButton(onBackClick = onBackButton) }
                )
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                when (state.screenType) {
                    AuthorizationScanMode.Loading -> LoadingState(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        label = stringResource(R.string.qrcode_scan_in_progress)
                    )

                    AuthorizationScanMode.CameraView -> CameraView(
                        qrCodeAnalyzer = qrCodeAnalyzer,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
