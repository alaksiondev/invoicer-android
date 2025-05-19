package io.github.alaksion.invoicer.features.intermediary.presentation.screen.update.screenshots

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.update.UpdateIntermediaryCallbacks
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.update.UpdateIntermediaryMode
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.update.UpdateIntermediaryScreen
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.update.UpdateIntermediaryState
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import org.junit.Rule
import kotlin.test.Test

class UpdateIntermediaryScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun updateIntermediaryScreen_loading() {
        paparazzi.snapshot {
            TestContent(
                state = UpdateIntermediaryState(
                    mode = UpdateIntermediaryMode.Loading
                )
            )
        }
    }

    @Test
    fun updateIntermediaryScreen_error() {
        paparazzi.snapshot {
            TestContent(
                state = UpdateIntermediaryState(
                    mode = UpdateIntermediaryMode.Error
                )
            )
        }
    }

    @Test
    fun updateIntermediaryScreen_content() {
        paparazzi.snapshot {
            TestContent(
                state = UpdateIntermediaryState(
                    mode = UpdateIntermediaryMode.Content,
                    name = "Intermediary name",
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
    fun updateIntermediaryScreen_submit() {
        paparazzi.snapshot {
            TestContent(
                state = UpdateIntermediaryState(
                    mode = UpdateIntermediaryMode.Content,
                    name = "Intermediary name",
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
        state: UpdateIntermediaryState
    ) {
        InvoicerTheme {
            UpdateIntermediaryScreen("")
                .StateContent(
                    state = state,
                    snackbarHostState = SnackbarHostState(),
                    callbacks = UpdateIntermediaryCallbacks(
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
