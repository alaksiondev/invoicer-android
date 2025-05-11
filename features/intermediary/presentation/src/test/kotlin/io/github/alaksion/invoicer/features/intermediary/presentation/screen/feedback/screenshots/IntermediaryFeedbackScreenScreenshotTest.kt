package io.github.alaksion.invoicer.features.intermediary.presentation.screen.feedback.screenshots

import app.cash.paparazzi.Paparazzi
import foundation.designsystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.feedback.IntermediaryFeedbackScreen
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.feedback.IntermediaryFeedbackType
import org.junit.Rule
import org.junit.Test

class IntermediaryFeedbackScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun beneficiaryFeedback_success() {
        paparazzi.snapshot {
            InvoicerTheme {
                IntermediaryFeedbackScreen(
                    type = IntermediaryFeedbackType.CreateSuccess
                ).StateContent { }
            }
        }
    }

}
