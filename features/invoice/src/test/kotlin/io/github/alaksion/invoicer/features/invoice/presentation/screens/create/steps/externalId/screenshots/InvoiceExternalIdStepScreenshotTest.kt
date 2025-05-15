package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId.InvoiceExternalIdState
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId.InvoiceExternalIdStep
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import org.junit.Rule
import kotlin.test.Test

class InvoiceExternalIdStepScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun external_id_default() {
        paparazzi.snapshot {
            TestContent(
                state = InvoiceExternalIdState(
                    externalId = "",
                )
            )
        }
    }

    @Test
    fun external_id_filled() {
        paparazzi.snapshot {
            TestContent(
                state = InvoiceExternalIdState(
                    externalId = "12345",
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: InvoiceExternalIdState
    ) {
        InvoicerTheme {
            InvoiceExternalIdStep()
                .StateContent(
                    onSubmit = {},
                    onUpdate = {},
                    onBack = {},
                    state = state
                )
        }
    }
}
