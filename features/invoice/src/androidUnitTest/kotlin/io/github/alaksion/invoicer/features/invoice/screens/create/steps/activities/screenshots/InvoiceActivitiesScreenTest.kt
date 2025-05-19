package io.github.alaksion.invoicer.features.invoice.screens.create.steps.activities.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.InvoiceActivitiesCallbacks
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.InvoiceActivitiesScreen
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.InvoiceActivitiesState
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.testUtil.MultiplatformSnapshot
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test

internal class InvoiceActivitiesScreenTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun invoice_activity_default() {
        paparazzi.snapshot {
            TestContent(
                InvoiceActivitiesState()
            )
        }
    }

    @Test
    fun invoice_activity_filled() {
        paparazzi.snapshot {
            TestContent(
                InvoiceActivitiesState(
                    activities = persistentListOf(
                        CreateInvoiceActivityUiModel(
                            description = "Activity 1",
                            unitPrice = 10,
                            quantity = 2,
                        ),
                    )
                )
            )
        }
    }

    @Composable
    fun TestContent(
        state: InvoiceActivitiesState
    ) {
        MultiplatformSnapshot {
            InvoicerTheme {
                InvoiceActivitiesScreen()
                    .StateContent(
                        state = state,
                        snackbarHostState = SnackbarHostState(),
                        callbacks = InvoiceActivitiesCallbacks(
                            onChangeDescription = { },
                            onChangeUnitPrice = { },
                            onChangeQuantity = { },
                            onDelete = { },
                            onClearForm = { },
                            onAddActivity = { },
                            onBack = { },
                            onContinue = { }
                        ),
                    )
            }
        }
    }
}
