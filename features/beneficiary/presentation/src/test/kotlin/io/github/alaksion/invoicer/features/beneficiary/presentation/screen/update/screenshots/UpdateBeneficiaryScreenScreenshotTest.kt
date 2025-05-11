package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import foundation.designsystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update.UpdateBeneficiaryCallbacks
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update.UpdateBeneficiaryMode
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update.UpdateBeneficiaryScreen
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update.UpdateBeneficiaryState
import org.junit.Rule
import kotlin.test.Test

class UpdateBeneficiaryScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun updateBeneficiaryScreen_loading() {
        paparazzi.snapshot {
            TestContent(
                state = UpdateBeneficiaryState(
                    mode = UpdateBeneficiaryMode.Loading
                )
            )
        }
    }

    @Test
    fun updateBeneficiaryScreen_error() {
        paparazzi.snapshot {
            TestContent(
                state = UpdateBeneficiaryState(
                    mode = UpdateBeneficiaryMode.Error
                )
            )
        }
    }

    @Test
    fun updateBeneficiaryScreen_content() {
        paparazzi.snapshot {
            TestContent(
                state = UpdateBeneficiaryState(
                    mode = UpdateBeneficiaryMode.Content,
                    name = "Beneficiary name",
                    bankName = "Bank name",
                    bankAddress = "Bank Address",
                    swift = "32939213",
                    iban = "12313",
                    isButtonLoading = false
                )
            )
        }
    }

    @Test
    fun updateBeneficiaryScreen_submit() {
        paparazzi.snapshot {
            TestContent(
                state = UpdateBeneficiaryState(
                    mode = UpdateBeneficiaryMode.Content,
                    name = "Beneficiary name",
                    bankName = "Bank name",
                    bankAddress = "Bank Address",
                    swift = "32939213",
                    iban = "12313",
                    isButtonLoading = true
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: UpdateBeneficiaryState
    ) {
        InvoicerTheme {
            UpdateBeneficiaryScreen("")
                .StateContent(
                    state = state,
                    snackBarHostState = SnackbarHostState(),
                    callBacks = UpdateBeneficiaryCallbacks(
                        onBack = { },
                        onChangeName = { },
                        onChangeBankName = { },
                        onChangeBankAddress = { },
                        onChangeSwift = { },
                        onChangeIban = { },
                        onSubmit = { },
                        onRetry = { },
                    ),
                )
        }
    }
}
