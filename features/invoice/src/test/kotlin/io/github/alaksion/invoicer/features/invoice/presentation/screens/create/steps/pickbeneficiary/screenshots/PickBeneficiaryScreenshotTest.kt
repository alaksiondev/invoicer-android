package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import foundation.designsystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary.BeneficiarySelection
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary.PickBeneficiaryScreen
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary.PickBeneficiaryState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Instant
import org.junit.Rule
import kotlin.test.Test

class PickBeneficiaryScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun pick_beneficiary_empty() {
        paparazzi.snapshot {
            TestContent(
                state = PickBeneficiaryState()
            )
        }
    }

    @Test
    fun pick_beneficiary_filled() {
        paparazzi.snapshot {
            TestContent(
                state = PickBeneficiaryState(
                    beneficiaries = beneficiaries
                )
            )
        }
    }

    @Test
    fun pick_beneficiary_filled_selected() {
        paparazzi.snapshot {
            TestContent(
                state = PickBeneficiaryState(
                    beneficiaries = beneficiaries,
                    selection = BeneficiarySelection.Existing(
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
                state = PickBeneficiaryState(
                    beneficiaries = beneficiaries,
                    selection = BeneficiarySelection.None
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: PickBeneficiaryState
    ) {
        InvoicerTheme {
            PickBeneficiaryScreen()
                .StateContent(
                    state = state,
                    onBack = { },
                    onContinue = { },
                    onSelect = { },
                    onRetry = { }
                )
        }
    }

    companion object {
        private val beneficiaries = persistentListOf(
            BeneficiaryModel(
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
