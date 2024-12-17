package features.home.presentation.tabs.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class WelcomeCallbacks(
    val onInvoiceClick: () -> Unit,
)

@Composable
internal fun rememberWelcomeCallbacks(
    onInvoiceClick: () -> Unit,
): WelcomeCallbacks {
    return remember {
        WelcomeCallbacks(
            onInvoiceClick = onInvoiceClick,
        )
    }
}