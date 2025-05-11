package io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import foundation.designsystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceListItem
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.InvoiceListScreen
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListMode
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListState
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.rememberInvoiceListCallbacks
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Instant
import org.junit.Rule
import org.junit.Test

class InvoiceListScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun invoiceList_empty() {
        paparazzi.snapshot {
            TestContent(
                state = InvoiceListState(
                    mode = InvoiceListMode.Content,
                    invoices = persistentListOf()
                )
            )
        }
    }

    @Test
    fun invoiceList_filled() {
        paparazzi.snapshot {
            TestContent(
                state = InvoiceListState(
                    mode = InvoiceListMode.Content,
                    invoices = persistentListOf(
                        InvoiceListItem(
                            id = "1",
                            externalId = "ExternalId",
                            senderCompany = "SenderCompany",
                            recipientCompany = "Recipient company",
                            issueDate = Instant.parse("2023-10-01T00:00:00Z"),
                            dueDate = Instant.parse("2023-10-01T00:00:00Z"),
                            createdAt = Instant.parse("2023-10-01T00:00:00Z"),
                            updatedAt = Instant.parse("2023-10-01T00:00:00Z"),
                            totalAmount = 100L,
                        ),
                    )
                )
            )
        }
    }

    @Test
    fun invoiceList_loading() {
        paparazzi.snapshot {
            TestContent(
                state = InvoiceListState(
                    mode = InvoiceListMode.Loading,
                    invoices = persistentListOf()
                )
            )
        }
    }

    @Test
    fun invoiceList_error() {
        paparazzi.snapshot {
            TestContent(
                state = InvoiceListState(
                    mode = InvoiceListMode.Error,
                    invoices = persistentListOf()
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: InvoiceListState
    ) {
        InvoicerTheme {
            InvoiceListScreen()
                .StateContent(
                    state = state,
                    callbacks = rememberInvoiceListCallbacks(
                        onClose = {},
                        onRetry = {},
                        onClickInvoice = {},
                        onClickCreateInvoice = {},
                        onNextPage = {},
                    ),
                    snackbarHostState = SnackbarHostState(),
                )
        }
    }
}