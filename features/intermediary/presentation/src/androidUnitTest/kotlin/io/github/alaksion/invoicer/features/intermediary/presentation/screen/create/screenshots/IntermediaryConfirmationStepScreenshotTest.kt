package io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryState
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.steps.IntermediaryConfirmationStep
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.testUtil.MultiplatformSnapshot
import org.junit.Rule
import org.junit.Test

class IntermediaryConfirmationStepScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun confirmationScreen_default() {
        paparazzi.snapshot {
            TestContent()
        }
    }

    @Test
    fun confirmationScreen_loading() {
        paparazzi.snapshot {
            TestContent(
                isSubmitting = true
            )
        }
    }

    @Composable
    private fun TestContent(
        name: String = "Beneficiary",
        swift: String = "912883",
        iban: String = "GB29NWBK60161331926819",
        bankName: String = "Bank Name",
        bankAddress: String = "Bank Address",
        isSubmitting: Boolean = false,
    ) {
        MultiplatformSnapshot {
            InvoicerTheme {
                IntermediaryConfirmationStep()
                    .StateContent(
                        state = CreateIntermediaryState(
                            name = name,
                            swift = swift,
                            iban = iban,
                            bankName = bankName,
                            bankAddress = bankAddress,
                            isSubmitting = isSubmitting,
                        ),
                        onContinue = { },
                        onBack = { },
                        snackBarHostState = SnackbarHostState(),
                    )
            }
        }
    }
}
