package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list

import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiariesModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
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
class BeneficiaryListScreenModelTest {

    private val beneficiaryRepository = mockk<BeneficiaryRepository>()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: BeneficiaryListScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        viewModel = BeneficiaryListScreenModel(
            beneficiaryRepository = beneficiaryRepository,
            dispatcher = dispatcher
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load first page successfully`() = runTest {
        val mockResponse = BeneficiariesModel(
            items = beneficiaries,
            nextPage = 2,
            itemCount = 2
        )
        coEvery { beneficiaryRepository.getBeneficiaries(any(), any()) } returns mockResponse

        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(BeneficiaryListMode.Content, state.mode)
        assertEquals(mockResponse.items, state.beneficiaries)
        assertTrue(viewModel.state.value.isNextPageLoading.not())
    }

    @Test
    fun `should handle error when loading first page`() = runTest {
        coEvery {
            beneficiaryRepository.getBeneficiaries(
                any(),
                any()
            )
        } throws IllegalStateException()

        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(BeneficiaryListMode.Error, state.mode)
    }

    companion object {
        val beneficiaryStub = BeneficiaryModel(
            name = "John Doe",
            iban = "IBAN123456789",
            swift = "SWIFT123",
            bankName = "Example Bank",
            bankAddress = "123 Bank Street",
            id = "1",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-02T00:00:00Z")
        )
        val beneficiaryStub2 = BeneficiaryModel(
            name = "John Doe",
            iban = "IBAN123456789",
            swift = "SWIFT123",
            bankName = "Example Bank",
            bankAddress = "123 Bank Street",
            id = "1",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-02T00:00:00Z")
        )
        val beneficiaries = listOf(
            beneficiaryStub,
            beneficiaryStub2
        )
    }
}
