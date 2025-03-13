package foundation.designsystem.components.preview

import androidx.compose.runtime.Composable
import foundation.designsystem.theme.InvoicerTheme

@Composable
fun ThemeContainer(
    content: @Composable () -> Unit
) {
    InvoicerTheme {
        content()
    }
}