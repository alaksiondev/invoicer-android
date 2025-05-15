package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.feedback.screenshots

import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.feedback.BeneficiaryFeedbackScreen
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.feedback.BeneficiaryFeedbackType
import org.junit.Rule
import org.junit.Test

class BeneficiaryFeedbackScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun beneficiaryFeedback_success() {
        paparazzi.snapshot {
            InvoicerTheme {
                BeneficiaryFeedbackScreen(
                    type = BeneficiaryFeedbackType.CreateSuccess
                ).StateContent { }
            }
        }
    }

}
