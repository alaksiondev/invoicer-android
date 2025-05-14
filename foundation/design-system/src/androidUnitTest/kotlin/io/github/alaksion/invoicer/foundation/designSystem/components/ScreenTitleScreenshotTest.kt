package io.github.alaksion.invoicer.foundation.designSystem.components

import io.github.alaksion.invoicer.foundation.designSystem.configs.InvoicerPaparazziConfig
import io.github.alaksion.invoicer.foundation.designSystem.configs.invoicerSnapshot
import org.junit.Rule
import org.junit.Test

class ScreenTitleScreenshotTest {
    @get:Rule
    val paparazzi = InvoicerPaparazziConfig

    @Test
    fun titleAndSubtitle() {
        paparazzi.invoicerSnapshot {
            ScreenTitle(
                title = "Title",
                subTitle = "Subtitle"
            )
        }
    }

    @Test
    fun titleOnly() {
        paparazzi.invoicerSnapshot {
            ScreenTitle(
                title = "Title",
                subTitle = null
            )
        }
    }
}
