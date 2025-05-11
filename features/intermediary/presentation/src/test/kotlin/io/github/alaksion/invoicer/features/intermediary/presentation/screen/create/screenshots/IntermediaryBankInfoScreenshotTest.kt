package io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import foundation.designsystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.steps.IntermediaryBankInfoStep
import org.junit.Rule
import kotlin.test.Test

class IntermediaryBankInfoScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun bankInfoScreen_default() {
        paparazzi.snapshot {
            TestContent(
                address = "",
                bankName = "",
                buttonEnabled = false
            )
        }
    }

    @Test
    fun bankInfoScreen_filled() {
        paparazzi.snapshot {
            TestContent(
                address = "Address",
                bankName = "Bank Name",
                buttonEnabled = true
            )
        }
    }

    @Composable
    private fun TestContent(
        address: String,
        bankName: String,
        buttonEnabled: Boolean,
    ) {
        InvoicerTheme {
            IntermediaryBankInfoStep()
                .StateContent(
                    address = address,
                    bankName = bankName,
                    buttonEnabled = buttonEnabled,
                    onAddressChange = {},
                    onBankNameChange = { },
                    onBack = { },
                    onContinue = {}
                )
        }
    }
}
