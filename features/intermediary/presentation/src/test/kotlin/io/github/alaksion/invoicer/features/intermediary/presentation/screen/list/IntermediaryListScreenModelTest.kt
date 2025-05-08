package io.github.alaksion.invoicer.features.intermediary.presentation.screen.list

import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.IntermediaryListMode
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.IntermediaryListScreenModel
import io.github.alaksion.invoicer.features.intermediary.services.domain.model.IntermediaryModel
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Instant
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class IntermediaryListScreenModelTest {

    private val intermediaryRepository = mockk<IntermediaryRepository>()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: IntermediaryListScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        viewModel = IntermediaryListScreenModel(
            intermediaryRepository = intermediaryRepository,
            dispatcher = dispatcher
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load first page successfully`() = runTest {
        val mockResponse = intermediaries
        coEvery { intermediaryRepository.getIntermediaries(any(), any()) } returns mockResponse

        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(IntermediaryListMode.Content, state.mode)
        assertEquals(mockResponse, state.beneficiaries)
        assertTrue(viewModel.state.value.isNextPageLoading.not())
    }

    @Test
    fun `should handle error when loading first page`() = runTest {
        coEvery {
            intermediaryRepository.getIntermediaries(
                any(),
                any()
            )
        } throws IllegalStateException()

        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(IntermediaryListMode.Error, state.mode)
    }

    companion object {
        val intermediaryStub = IntermediaryModel(
            name = "John Doe",
            iban = "IBAN123456789",
            swift = "SWIFT123",
            bankName = "Example Bank",
            bankAddress = "123 Bank Street",
            id = "1",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-02T00:00:00Z")
        )
        val intermediaryStub2 = IntermediaryModel(
            name = "John Doe",
            iban = "IBAN123456789",
            swift = "SWIFT123",
            bankName = "Example Bank",
            bankAddress = "123 Bank Street",
            id = "1",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-02T00:00:00Z")
        )
        val intermediaries = listOf(
            intermediaryStub,
            intermediaryStub2
        )
    }
}