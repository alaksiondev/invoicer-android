package io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryState
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.steps.IntermediaryDocumentStep
import org.junit.Rule
import org.junit.Test

class IntermediaryDocumentStepScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun documentStep_default() {
        paparazzi.snapshot {
            TestContent()
        }
    }

    @Test
    fun documentStep_filled() {
        paparazzi.snapshot {
            TestContent(
                state = CreateIntermediaryState(
                    iban = "GB29NWBK60161331926819",
                    swift = "912883",
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: CreateIntermediaryState = CreateIntermediaryState(),
        buttonEnabled: Boolean = true,
    ) {
        InvoicerTheme {
            IntermediaryDocumentStep()
                .StateContent(
                    state = state,
                    onBack = { },
                    onContinue = { },
                    buttonEnabled = buttonEnabled,
                    onIbanChange = {},
                    onSwiftChange = {},
                )
        }
    }

}
