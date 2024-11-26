package foundation.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun <T> EventEffect(
    publisher: EventAware<T>,
    onEvent: (T) -> Unit
) {
    val stackState by publisher.stack.collectAsStateWithLifecycle()

    LaunchedEffect(publisher.stack) {
        stackState.firstOrNull()?.let { event ->
            onEvent(event.data)
            publisher.consume(event)
        }
    }
}