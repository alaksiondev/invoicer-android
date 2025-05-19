package io.github.alaksion.invoicer.features.intermediary.presentation.screen.details.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.details.IntermediaryDetailsMode
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.details.IntermediaryDetailsScreen
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.details.IntermediaryDetailsState
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.details.IntermediaryErrorType
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.testUtil.MultiplatformSnapshot
import org.junit.Rule
import org.junit.Test

class IntermediaryDetailsScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun beneficiaryDetails_content() {
        paparazzi.snapshot {
            TestContent(
                state = IntermediaryDetailsState(
                    name = "Intermediary",
                    iban = "DE89370400440532013000",
                    swift = "DEUTDEDBFRA",
                    bankName = "Deutsche Bank",
                    bankAddress = "Frankfurt am Main",
                    createdAt = "2023-10-01",
                    updatedAt = "2023-10-01",
                    mode = IntermediaryDetailsMode.Content
                )
            )
        }
    }

    @Test
    fun beneficiaryDetails_loading() {
        paparazzi.snapshot {
            TestContent(
                state = IntermediaryDetailsState(
                    mode = IntermediaryDetailsMode.Loading
                )
            )
        }
    }

    @Test
    fun beneficiaryDetails_error_generic() {
        paparazzi.snapshot {
            TestContent(
                state = IntermediaryDetailsState(
                    mode = IntermediaryDetailsMode.Error(
                        type = IntermediaryErrorType.Generic
                    )
                )
            )
        }
    }

    @Test
    fun beneficiaryDetails_error_not_found() {
        paparazzi.snapshot {
            TestContent(
                state = IntermediaryDetailsState(
                    mode = IntermediaryDetailsMode.Error(
                        type = IntermediaryErrorType.NotFound
                    )
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: IntermediaryDetailsState,
        showDeleteDialog: Boolean = false
    ) {
        MultiplatformSnapshot {
            InvoicerTheme {
                IntermediaryDetailsScreen("").StateContent(
                    state = state,
                    onBack = {},
                    onRetry = {},
                    snackbarHost = SnackbarHostState(),
                    showDeleteDialog = showDeleteDialog,
                    onRequestDelete = { },
                    onConfirmDelete = { },
                    onDismissDelete = { },
                    onRequestEdit = { },
                )
            }
        }
    }
}
