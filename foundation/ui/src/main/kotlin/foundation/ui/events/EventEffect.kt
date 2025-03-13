package foundation.ui.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun <T> EventEffect(
    publisher: EventAware<T>,
    onEvent: (T) -> Unit
) {
    LaunchedEffect(publisher.stack) {
        publisher.stack.collect {
            it.firstOrNull()?.let { event ->
                onEvent(event.data)
                publisher.consume(event)
            }
        }
    }
}