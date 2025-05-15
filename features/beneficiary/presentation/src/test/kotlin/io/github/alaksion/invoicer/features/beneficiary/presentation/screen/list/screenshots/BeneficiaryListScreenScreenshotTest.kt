package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list.BeneficiaryListMode
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list.BeneficiaryListScreen
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list.BeneficiaryListState
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list.rememberBeneficiaryListCallbacks
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Instant
import org.junit.Rule
import org.junit.Test

class BeneficiaryListScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun beneficiaryList_loading() {
        paparazzi.snapshot {
            TestContent(
                state = BeneficiaryListState(
                    mode = BeneficiaryListMode.Loading
                )
            )
        }
    }

    @Test
    fun beneficiaryList_empty() {
        paparazzi.snapshot {
            TestContent(
                state = BeneficiaryListState(
                    mode = BeneficiaryListMode.Content
                )
            )
        }
    }

    @Test
    fun beneficiaryList_one_item() {
        paparazzi.snapshot {
            TestContent(
                state = BeneficiaryListState(
                    mode = BeneficiaryListMode.Content,
                    beneficiaries = persistentListOf(
                        BeneficiaryModel(
                            name = "Name",
                            iban = "IBAN",
                            swift = "1231312",
                            bankName = "Bank Name",
                            bankAddress = "Bank Address",
                            id = "123",
                            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
                            updatedAt = Instant.parse("2023-01-01T00:00:00Z")
                        )
                    )
                )
            )
        }
    }

    @Test
    fun beneficiaryList_multi_item() {
        paparazzi.snapshot {
            TestContent(
                state = BeneficiaryListState(
                    mode = BeneficiaryListMode.Content,
                    beneficiaries = (1..10).map {
                        BeneficiaryModel(
                            name = "Name",
                            iban = "IBAN",
                            swift = "1231312",
                            bankName = "Bank Name",
                            bankAddress = "Bank Address",
                            id = "1-$it",
                            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
                            updatedAt = Instant.parse("2023-01-01T00:00:00Z")
                        )
                    }.toPersistentList()
                )
            )
        }
    }

    @Test
    fun beneficiaryList_multi_error() {
        paparazzi.snapshot {
            TestContent(
                state = BeneficiaryListState(
                    mode = BeneficiaryListMode.Error,
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: BeneficiaryListState
    ) {
        InvoicerTheme {
            BeneficiaryListScreen()
                .StateContent(
                    state = state,
                    callbacks = rememberBeneficiaryListCallbacks(
                        onClose = {},
                        onRetry = {},
                        onCreate = {},
                        onItemClick = {},
                    )
                )
        }
    }

}
