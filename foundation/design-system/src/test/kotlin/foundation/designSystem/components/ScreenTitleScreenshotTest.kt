package foundation.designSystem.components

import foundation.designSystem.configs.InvoicerPaparazziConfig
import foundation.designSystem.configs.invoicerSnapshot
import foundation.designsystem.components.ScreenTitle
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