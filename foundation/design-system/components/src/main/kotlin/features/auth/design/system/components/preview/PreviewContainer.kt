package features.auth.design.system.components.preview

import androidx.compose.runtime.Composable
import foundation.design.system.theme.InvoicerTheme

@Composable
fun PreviewContainer(
    content: @Composable () -> Unit
) {
    InvoicerTheme {
        content()
    }
}