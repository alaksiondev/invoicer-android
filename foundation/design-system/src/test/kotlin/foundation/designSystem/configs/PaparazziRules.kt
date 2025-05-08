package foundation.designSystem.configs

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import foundation.designsystem.theme.InvoicerTheme

val InvoicerPaparazziConfig = Paparazzi(
    maxPercentDifference = 0.0
)

fun Paparazzi.invoicerSnapshot(
    name: String? = null,
    content: @Composable () -> Unit,
) {
    snapshot(name = name) {
        InvoicerTheme {
            content()
        }
    }
}