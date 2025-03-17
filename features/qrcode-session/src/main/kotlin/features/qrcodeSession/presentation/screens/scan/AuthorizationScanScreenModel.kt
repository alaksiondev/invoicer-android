package features.qrcodeSession.presentation.screens.scan

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.qrcodeSession.util.executeWithMinDelay
import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher
import foundation.validator.impl.UuidValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class AuthorizationScanScreenModel(
    private val uuidValidator: UuidValidator,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel,
    EventAware<AuthorizationScanEvents> by EventPublisher() {

    private var processing: Boolean = false
    private val _state = MutableStateFlow(AuthorizationScanState())
    val state: StateFlow<AuthorizationScanState> = _state

    fun onScanSuccess(rawData: String) {
        if (processing) return
        processing = true
        screenModelScope.launch(dispatcher) {
            val isValid = executeWithMinDelay { uuidValidator.validate(rawData).not() }

            if (isValid) {
                publish(AuthorizationScanEvents.InvalidCode)
                return@launch
            } else {
                publish(AuthorizationScanEvents.ProceedToConfirmation(rawData))
            }
        }.invokeOnCompletion { processing = false }
    }

    fun onScanError() {
        screenModelScope.launch(dispatcher) {
            publish(AuthorizationScanEvents.InvalidCode)
        }
    }
}