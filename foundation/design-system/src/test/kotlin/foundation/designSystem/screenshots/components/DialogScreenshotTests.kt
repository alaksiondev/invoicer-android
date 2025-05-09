package foundation.designSystem.screenshots.components

import foundation.designSystem.screenshots.configs.InvoicerPaparazziConfig
import foundation.designSystem.screenshots.configs.invoicerSnapshot
import foundation.designsystem.components.DialogVariant
import foundation.designsystem.components.InvoicerDialog
import org.junit.Rule
import org.junit.Test

class DialogScreenshotTests {

    @get:Rule
    val paparazzi = InvoicerPaparazziConfig

    @Test
    fun dialogVariantError() {
        paparazzi.invoicerSnapshot {
            InvoicerDialog(
                onDismiss = {},
                variant = DialogVariant.Error,
                title = "Error",
                description = "This is an error message"
            )
        }
    }

}