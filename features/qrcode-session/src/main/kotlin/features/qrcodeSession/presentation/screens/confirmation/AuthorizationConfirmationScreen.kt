package features.qrcodeSession.presentation.screens.confirmation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
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
import features.qrcodeSession.presentation.screens.confirmation.components.CodeDetails
import features.qrcodeSession.presentation.screens.success.AuthorizationSuccessScreen
import foundation.designsystem.components.LoadingState
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.components.feedback.Feedback
import foundation.designsystem.tokens.Spacing
import foundation.ui.events.EventEffect

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

        EventEffect(screenModel) { event ->
            when (event) {
                AuthorizationConfirmationEvents.Authorized -> navigator?.push(
                    AuthorizationSuccessScreen()
                )
            }
        }

        StateContent(
            state = state.value,
            onBack = { navigator?.pop() },
            onAuthorize = {
                screenModel.authorizeQrCode(codeContentId)
            },
            onRetryLoadData = {
                screenModel.getCodeDetails(codeContentId)
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: AuthorizationConfirmationState,
        onBack: () -> Unit,
        onAuthorize: () -> Unit,
        onRetryLoadData: () -> Unit,
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
                    .padding(Spacing.medium)
            ) {
                when (state.mode) {
                    AuthorizationConfirmationMode.Content -> CodeDetails(
                        modifier = Modifier
                            .fillMaxWidth(),
                        qrCodeAgent = state.qrCodeAgent,
                        qrCodeIp = state.qrCodeIp,
                        qrCodeExpiration = state.qrCodeExpiration,
                        qrCodeEmission = state.qrCodeEmission,
                        onAuthorize = onAuthorize
                    )

                    AuthorizationConfirmationMode.Error -> Feedback(
                        title = stringResource(R.string.qr_code_details_error_title),
                        description = stringResource(R.string.qr_code_details_error_description),
                        primaryActionText = stringResource(R.string.qr_code_details_error_primary_cta),
                        onPrimaryAction = onRetryLoadData,
                        secondaryActionText = stringResource(R.string.qr_code_details_error_secondary_cta),
                        onSecondaryAction = onBack,
                        icon = Icons.Default.ErrorOutline
                    )

                    AuthorizationConfirmationMode.AuthorizeError -> Feedback(
                        title = stringResource(R.string.qr_code_details_error_title),
                        description = stringResource(R.string.qr_code_details_error_description),
                        primaryActionText = stringResource(R.string.qr_code_details_error_primary_cta),
                        onPrimaryAction = onAuthorize,
                        secondaryActionText = stringResource(R.string.qr_code_details_error_secondary_cta),
                        onSecondaryAction = onBack,
                        icon = Icons.Default.ErrorOutline
                    )

                    AuthorizationConfirmationMode.Loading -> LoadingState(Modifier.weight(1f))
                }
            }
        }
    }

}
