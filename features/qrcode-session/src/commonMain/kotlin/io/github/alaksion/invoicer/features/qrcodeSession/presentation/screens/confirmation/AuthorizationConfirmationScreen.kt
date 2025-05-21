package io.github.alaksion.invoicer.features.qrcodeSession.presentation.screens.confirmation

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import invoicer.features.qrcode_session.generated.resources.Res
import invoicer.features.qrcode_session.generated.resources.qr_code_details
import invoicer.features.qrcode_session.generated.resources.qr_code_details_error_description
import invoicer.features.qrcode_session.generated.resources.qr_code_details_error_primary_cta
import invoicer.features.qrcode_session.generated.resources.qr_code_details_error_secondary_cta
import invoicer.features.qrcode_session.generated.resources.qr_code_details_error_title
import io.github.alaksion.invoicer.features.qrcodeSession.presentation.screens.confirmation.components.CodeDetails
import io.github.alaksion.invoicer.features.qrcodeSession.presentation.screens.success.AuthorizationSuccessScreen
import io.github.alaksion.invoicer.foundation.designSystem.components.LoadingState
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.components.feedback.Feedback
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import io.github.alaksion.invoicer.foundation.ui.events.EventEffect
import org.jetbrains.compose.resources.stringResource

internal data class AuthorizationConfirmationScreen(
    private val codeContentId: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<AuthorizationConfirmationScreenModel>()
        val state = screenModel.state.collectAsState()
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
                    title = { Text(stringResource(Res.string.qr_code_details)) },
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
                        title = stringResource(Res.string.qr_code_details_error_title),
                        description = stringResource(Res.string.qr_code_details_error_description),
                        primaryActionText = stringResource(Res.string.qr_code_details_error_primary_cta),
                        onPrimaryAction = onRetryLoadData,
                        secondaryActionText = stringResource(Res.string.qr_code_details_error_secondary_cta),
                        onSecondaryAction = onBack,
                        icon = Icons.Default.ErrorOutline
                    )

                    AuthorizationConfirmationMode.AuthorizeError -> Feedback(
                        title = stringResource(Res.string.qr_code_details_error_title),
                        description = stringResource(Res.string.qr_code_details_error_description),
                        primaryActionText = stringResource(Res.string.qr_code_details_error_primary_cta),
                        onPrimaryAction = onAuthorize,
                        secondaryActionText = stringResource(Res.string.qr_code_details_error_secondary_cta),
                        onSecondaryAction = onBack,
                        icon = Icons.Default.ErrorOutline
                    )

                    AuthorizationConfirmationMode.Loading -> LoadingState(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }

}
