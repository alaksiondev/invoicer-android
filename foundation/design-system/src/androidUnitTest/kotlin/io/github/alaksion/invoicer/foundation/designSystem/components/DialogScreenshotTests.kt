package io.github.alaksion.invoicer.foundation.designSystem.components

import io.github.alaksion.invoicer.foundation.designSystem.configs.InvoicerPaparazziConfig
import io.github.alaksion.invoicer.foundation.designSystem.configs.invoicerSnapshot
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
