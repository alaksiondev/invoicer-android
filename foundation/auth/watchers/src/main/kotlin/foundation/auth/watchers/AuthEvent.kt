package foundation.auth.watchers

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface AuthEventSubscriber {
    val events: SharedFlow<AuthEvent>
}

interface AuthEventPublisher {
    suspend fun publish(event: AuthEvent)
}

sealed interface AuthEvent {
    data object SignIn : AuthEvent
    data class SignOff(val reason: String) : AuthEvent
}

internal object AuthManager : AuthEventPublisher, AuthEventSubscriber {

    private val _events = MutableSharedFlow<AuthEvent>(replay = 1)

    override suspend fun publish(event: AuthEvent) {
        _events.emit(event)
    }

    override val events: SharedFlow<AuthEvent>
        get() = _events
}