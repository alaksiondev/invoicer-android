package foundation.watchers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface AuthEventBus {
    suspend fun publishEvent(event: AuthEvent)
    suspend fun subscribe(): Flow<AuthEvent>
}

internal object AuthEventBusManager : AuthEventBus {

    // Setting buffer and replay just in case to avoid dead messages
    private val _events = MutableSharedFlow<AuthEvent>(
        replay = 10,
        extraBufferCapacity = 5
    )

    override suspend fun publishEvent(event: AuthEvent) {
        _events.emit(event)
    }

    override suspend fun subscribe(): Flow<AuthEvent> = _events.asSharedFlow()

}

sealed interface AuthEvent {
    data object SignedIn : AuthEvent
    data object SignedOut : AuthEvent
}