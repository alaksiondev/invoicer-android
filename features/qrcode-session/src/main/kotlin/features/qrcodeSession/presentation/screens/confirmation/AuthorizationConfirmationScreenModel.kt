package features.qrcodeSession.presentation.screens.confirmation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.qrcodeSession.domain.repository.QrCodeTokenRepository
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AuthorizationConfirmationScreenModel(
    private val qrCodeTokenRepository: QrCodeTokenRepository,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel, EventAware<AuthorizationConfirmationEvents> by EventPublisher() {

    private val _state = MutableStateFlow(AuthorizationConfirmationState())
    val state: StateFlow<AuthorizationConfirmationState> = _state

    fun getCodeDetails(contentId: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                qrCodeTokenRepository.getQrCodeDetails(token = contentId)
            }.handle(
                onSuccess = { qrCodeDetails ->
                    _state.value = _state.value.copy(
                        qrCodeAgent = qrCodeDetails.agent,
                        qrCodeIp = qrCodeDetails.ipAddress,
                        qrCodeExpiration = qrCodeDetails.expiresAt.toString(),
                        qrCodeEmission = qrCodeDetails.createdAt.toString(),
                        mode = AuthorizationConfirmationMode.Content
                    )
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            mode = AuthorizationConfirmationMode.Error
                        )
                    }
                },
                onStart = {
                    _state.value = _state.value.copy(
                        mode = AuthorizationConfirmationMode.Loading
                    )
                },
            )
        }
    }

    fun authorizeQrCode(contentId: String) {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                qrCodeTokenRepository.consumeQrCode(token = contentId)
            }.handle(
                onSuccess = {
                    publish(AuthorizationConfirmationEvents.Authorized)
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            mode = AuthorizationConfirmationMode.Error
                        )
                    }
                },
                onStart = {
                    _state.value = _state.value.copy(
                        mode = AuthorizationConfirmationMode.Loading
                    )
                },
            )
        }
    }
}