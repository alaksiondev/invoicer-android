package io.github.alaksion.invoicer.foundation.designSystem.components.preview

import androidx.compose.runtime.Composable
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme

@Composable
fun ThemeContainer(
    content: @Composable () -> Unit
) {
    InvoicerTheme {
        content()
    }
}
