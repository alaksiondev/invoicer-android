package io.github.alaksion.invoicer.features.invoice.screens.create.steps.pickintermediary.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.features.intermediary.services.domain.model.IntermediaryModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary.IntermediarySelection
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary.PickIntermediaryScreen
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary.PickIntermediaryState
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.foundation.testUtil.MultiplatformSnapshot
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Instant
import org.junit.Rule
import kotlin.test.Test

class PickIntermediaryScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun pick_beneficiary_empty() {
        paparazzi.snapshot {
            TestContent(
                state = PickIntermediaryState()
            )
        }
    }

    @Test
    fun pick_beneficiary_filled() {
        paparazzi.snapshot {
            TestContent(
                state = PickIntermediaryState(
                    intermediaries = beneficiaries
                )
            )
        }
    }

    @Test
    fun pick_beneficiary_filled_selected() {
        paparazzi.snapshot {
            TestContent(
                state = PickIntermediaryState(
                    intermediaries = beneficiaries,
                    selection = IntermediarySelection.Existing(
                        id = beneficiaries.first().id
                    )
                )
            )
        }
    }

    @Test
    fun pick_beneficiary_filled_none_selected() {
        paparazzi.snapshot {
            TestContent(
                state = PickIntermediaryState(
                    intermediaries = beneficiaries,
                    selection = IntermediarySelection.None
                )
            )
        }
    }

    @Test
    fun pick_beneficiary_filled_skip_selected() {
        paparazzi.snapshot {
            TestContent(
                state = PickIntermediaryState(
                    intermediaries = beneficiaries,
                    selection = IntermediarySelection.Ignore
                )
            )
        }
    }

    @Test
    fun pick_beneficiary_filled_new_selected() {
        paparazzi.snapshot {
            TestContent(
                state = PickIntermediaryState(
                    intermediaries = beneficiaries,
                    selection = IntermediarySelection.New
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: PickIntermediaryState
    ) {
        MultiplatformSnapshot {
            InvoicerTheme {
                PickIntermediaryScreen()
                    .StateContent(
                        state = state,
                        onBack = { },
                        onContinue = { },
                        onSelect = { },
                        onRetry = { }
                    )
            }
        }
    }

    companion object {
        private val beneficiaries = persistentListOf(
            IntermediaryModel(
                id = "1",
                name = "Beneficiary 1",
                iban = "123123",
                swift = "124142",
                bankName = "Bank",
                bankAddress = "Address",
                createdAt = Instant.parse("2023-01-01T00:00:00Z"),
                updatedAt = Instant.parse("2023-01-01T00:00:00Z")
            ),
        )
    }
}
