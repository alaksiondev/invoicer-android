package io.github.alaksion.invoicer.features.intermediary.presentation.screen.list.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import io.github.alaksion.invoicer.foundation.designSystem.theme.InvoicerTheme
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.IntermediaryListMode
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.IntermediaryListScreen
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.IntermediaryListState
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.rememberIntermediaryListCallbacks
import io.github.alaksion.invoicer.features.intermediary.services.domain.model.IntermediaryModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Instant
import org.junit.Rule
import org.junit.Test

class IntermediaryListScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun beneficiaryList_loading() {
        paparazzi.snapshot {
            TestContent(
                state = IntermediaryListState(
                    mode = IntermediaryListMode.Loading
                )
            )
        }
    }

    @Test
    fun beneficiaryList_empty() {
        paparazzi.snapshot {
            TestContent(
                state = IntermediaryListState(
                    mode = IntermediaryListMode.Content
                )
            )
        }
    }

    @Test
    fun beneficiaryList_one_item() {
        paparazzi.snapshot {
            TestContent(
                state = IntermediaryListState(
                    mode = IntermediaryListMode.Content,
                    beneficiaries = persistentListOf(
                        IntermediaryModel(
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
                state = IntermediaryListState(
                    mode = IntermediaryListMode.Content,
                    beneficiaries = (1..10).map {
                        IntermediaryModel(
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
                state = IntermediaryListState(
                    mode = IntermediaryListMode.Error,
                )
            )
        }
    }

    @Composable
    private fun TestContent(
        state: IntermediaryListState
    ) {
        InvoicerTheme {
            IntermediaryListScreen()
                .StateContent(
                    state = state,
                    callbacks = rememberIntermediaryListCallbacks(
                        onClose = {},
                        onRetry = {},
                        onCreate = {},
                        onItemClick = {},
                    )
                )
        }
    }

}
