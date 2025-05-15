package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company.InvoiceCompanyState
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company.InvoiceCompanyStep
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import org.junit.Rule
import kotlin.test.Test

class InvoiceCompanyStepScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun invoice_company_step_default() {
        paparazzi.snapshot {
            TestContent(
                InvoiceCompanyState()
            )
        }
    }

    @Test
    fun invoice_company_step_filled() {
        paparazzi.snapshot {
            TestContent(
                InvoiceCompanyState(
                    recipientName = "Recipient Name",
                    recipientAddress = "Recipient Address",
                    senderName = "Sender Name",
                    senderAddress = "Sender Address",
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: InvoiceCompanyState
    ) {
        InvoicerTheme {
            InvoiceCompanyStep()
                .StateContent(
                    state = state,
                    onBack = { },
                    onSubmit = {},
                    onChangeSenderName = {},
                    onChangeSenderAddress = {},
                    onChangeRecipientName = {},
                    onChangeRecipientAddress = {},
                )
        }
    }

}
