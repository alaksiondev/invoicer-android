package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.steps.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.CreateBeneficiaryState
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.steps.BeneficiaryDocumentStep
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.testUtil.MultiplatformSnapshot
import org.junit.Rule
import org.junit.Test

class BeneficiaryDocumentStepScreenshotTest {

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
                state = CreateBeneficiaryState(
                    iban = "GB29NWBK60161331926819",
                    swift = "912883",
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: CreateBeneficiaryState = CreateBeneficiaryState(),
        buttonEnabled: Boolean = true,
    ) {
        MultiplatformSnapshot {
            InvoicerTheme {
                BeneficiaryDocumentStep()
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
}
