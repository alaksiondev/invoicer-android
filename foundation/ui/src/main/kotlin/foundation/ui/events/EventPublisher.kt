package foundation.ui.events

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Event<T>(
    val data: T
) {
    @OptIn(ExperimentalUuidApi::class)
    val id = Uuid.random().toString()
}

interface EventAware<T> {
    suspend fun publish(event: T)
    fun consume(event: Event<T>)

    val stack: StateFlow<List<Event<T>>>
}

class EventPublisher<T> : EventAware<T> {

    private val _eventStack = MutableStateFlow(emptyList<Event<T>>())
    override val stack: StateFlow<List<Event<T>>> = _eventStack

    override suspend fun publish(event: T) {
        _eventStack.update {
            it + Event(event)
        }
    }

    override fun consume(event: Event<T>) {
        _eventStack.update { events ->
            events.filter { it.id != event.id }
        }
    }
}