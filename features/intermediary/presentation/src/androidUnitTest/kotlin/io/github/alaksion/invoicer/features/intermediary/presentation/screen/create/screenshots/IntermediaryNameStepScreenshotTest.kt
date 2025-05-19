package io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.steps.IntermediaryNameStep
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.testUtil.MultiplatformSnapshot
import org.junit.Rule
import org.junit.Test

class IntermediaryNameStepScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun nameStep_default() {
        paparazzi.snapshot {
            TestContent(
                name = ""
            )
        }
    }

    @Test
    fun nameStep_filled() {
        paparazzi.snapshot {
            TestContent(
                name = "Beneficiary name"
            )
        }
    }

    @Composable
    private fun TestContent(
        name: String
    ) {
        MultiplatformSnapshot {
            InvoicerTheme {
                IntermediaryNameStep()
                    .StateContent(
                        name = name,
                        onNameChange = { },
                        onBack = { },
                        onContinue = { },
                        buttonEnabled = false,
                    )
            }
        }
    }
}
