package features.invoice.presentation.screens.invoicelist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class InvoiceListCallbacks(
    val onClose: () -> Unit
)

@Composable
internal fun rememberInvoiceListCallbacks(
    onClose: () -> Unit
): InvoiceListCallbacks {
    return remember {
        InvoiceListCallbacks(
            onClose = onClose
        )
    }
}