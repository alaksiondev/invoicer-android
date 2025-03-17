package features.qrcodeSession.presentation.screens.confirmation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import feature.qrcodeSession.R
import features.qrcodeSession.presentation.screens.components.CodeDetails
import foundation.designsystem.components.LoadingState
import foundation.designsystem.components.buttons.BackButton

internal data class AuthorizationConfirmationScreen(
    private val codeContentId: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<AuthorizationConfirmationScreenModel>()
        val state = screenModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) {
            screenModel.getCodeDetails(codeContentId)
        }

        StateContent(
            state = state.value,
            onBack = { navigator?.pop() }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: AuthorizationConfirmationState,
        onBack: () -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.qr_code_details)) },
                    navigationIcon = {
                        BackButton(onBackClick = onBack)
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                when (state.mode) {
                    AuthorizationConfirmationMode.Content -> CodeDetails(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        qrCodeAgent = state.qrCodeAgent,
                        qrCodeIp = state.qrCodeIp,
                        qrCodeExpiration = state.qrCodeExpiration,
                        qrCodeEmission = state.qrCodeEmission,
                        onAuthorize = {}
                    )

                    AuthorizationConfirmationMode.Error -> Text(text = "Error")

                    AuthorizationConfirmationMode.Loading -> LoadingState(Modifier.weight(1f))
                }
                Text("confirmation screen")
            }
        }
    }

}
