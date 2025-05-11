package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.steps.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import foundation.designsystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.steps.BeneficiaryBankInfoStep
import org.junit.Rule
import kotlin.test.Test

class BeneficiaryBankInfoScreenshotTest {

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
            BeneficiaryBankInfoStep()
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
