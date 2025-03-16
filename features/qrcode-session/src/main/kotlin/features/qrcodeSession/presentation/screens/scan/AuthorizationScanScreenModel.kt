package features.qrcodeSession.presentation.screens.scan

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.qrcodeSession.domain.repository.QrCodeTokenRepository
import foundation.network.RequestError
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import foundation.validator.impl.UuidValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class AuthorizationScanScreenModel(
    private val uuidValidator: UuidValidator,
    private val qrCodeTokenRepository: QrCodeTokenRepository,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel,
    EventAware<AuthorizationScanEvents> by EventPublisher() {

    private var processing: Boolean = false
    private val _state = MutableStateFlow(AuthorizationScanState())
    val state: StateFlow<AuthorizationScanState> = _state

    private lateinit var qrCodeContentId: String

    fun onScanSuccess(rawData: String) {
        if (processing) return
        processing = true
        screenModelScope.launch(dispatcher) {
            if (uuidValidator.validate(rawData).not()) {
                publish(AuthorizationScanEvents.InvalidCode)
                return@launch
            }

            launchRequest {
                qrCodeTokenRepository.getQrCodeDetails(token = rawData)
            }.handle(
                onSuccess = { qrCodeDetails ->
                    qrCodeContentId = rawData
                    _state.value = _state.value.copy(
                        qrCodeAgent = qrCodeDetails.agent,
                        qrCodeIp = qrCodeDetails.ipAddress,
                        qrCodeExpiration = qrCodeDetails.expiresAt.toString(),
                        qrCodeEmission = qrCodeDetails.createdAt.toString(),
                        screenType = AuthorizationScanMode.QrCodeContent
                    )
                },
                onFailure = { error ->
                    if (error is RequestError.Http && error.httpCode == 404) {
                        publish(AuthorizationScanEvents.CodeNotFound)
                    } else {
                        publish(AuthorizationScanEvents.UnknownError)
                    }
                    _state.value = _state.value.copy(
                        screenType = AuthorizationScanMode.CameraView
                    )
                },
                onStart = {
                    _state.value = _state.value.copy(
                        screenType = AuthorizationScanMode.Loading
                    )
                },
            )
        }.invokeOnCompletion { processing = false }
    }

    fun onScanError() {
        screenModelScope.launch(dispatcher) {
            publish(AuthorizationScanEvents.InvalidCode)
        }
    }
}