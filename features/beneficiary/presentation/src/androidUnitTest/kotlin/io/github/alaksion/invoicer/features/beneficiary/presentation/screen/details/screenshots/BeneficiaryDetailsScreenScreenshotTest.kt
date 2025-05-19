package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details.BeneficiaryDetailsMode
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details.BeneficiaryDetailsScreen
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details.BeneficiaryDetailsState
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details.BeneficiaryErrorType
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.testUtil.MultiplatformSnapshot
import org.junit.Rule
import org.junit.Test

class BeneficiaryDetailsScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun beneficiaryDetails_content() {
        paparazzi.snapshot {
            TestContent(
                state = BeneficiaryDetailsState(
                    name = "Beneficiary",
                    iban = "DE89370400440532013000",
                    swift = "DEUTDEDBFRA",
                    bankName = "Deutsche Bank",
                    bankAddress = "Frankfurt am Main",
                    createdAt = "2023-10-01",
                    updatedAt = "2023-10-01",
                    mode = BeneficiaryDetailsMode.Content
                )
            )
        }
    }

    @Test
    fun beneficiaryDetails_loading() {
        paparazzi.snapshot {
            TestContent(
                state = BeneficiaryDetailsState(
                    mode = BeneficiaryDetailsMode.Loading
                )
            )
        }
    }

    @Test
    fun beneficiaryDetails_error_generic() {
        paparazzi.snapshot {
            TestContent(
                state = BeneficiaryDetailsState(
                    mode = BeneficiaryDetailsMode.Error(
                        type = BeneficiaryErrorType.Generic
                    )
                )
            )
        }
    }

    @Test
    fun beneficiaryDetails_error_not_found() {
        paparazzi.snapshot {
            TestContent(
                state = BeneficiaryDetailsState(
                    mode = BeneficiaryDetailsMode.Error(
                        type = BeneficiaryErrorType.NotFound
                    )
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: BeneficiaryDetailsState,
        showDeleteDialog: Boolean = false
    ) {
        MultiplatformSnapshot {
            InvoicerTheme {
                BeneficiaryDetailsScreen("").StateContent(
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
