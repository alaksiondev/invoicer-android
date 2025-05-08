package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details

import foundation.network.RequestError
import foundation.watchers.RefreshBeneficiaryPublisher
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
import io.github.alaksion.invoicer.foundation.utils.date.defaultFormat
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class BeneficiaryDetailsScreenModelTest {

    private val beneficiaryRepository = mockk<BeneficiaryRepository>()
    private val refreshBeneficiaryPublisher = RefreshBeneficiaryPublisher()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: BeneficiaryDetailsScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        viewModel = BeneficiaryDetailsScreenModel(
            beneficiaryRepository = beneficiaryRepository,
            dispatcher = dispatcher,
            refreshBeneficiaryPublisher = refreshBeneficiaryPublisher
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should initialize state successfully`() = runTest {
        val createdAt = Instant.parse("2023-01-01T00:00:00Z")
        val updatedAt = Instant.parse("2023-01-02T00:00:00Z")
        val mockResponse = BeneficiaryModel(
            name = "John Doe",
            iban = "IBAN123",
            swift = "SWIFT123",
            bankName = "Bank Name",
            bankAddress = "123 Bank St",
            createdAt = createdAt,
            updatedAt = updatedAt,
            id = "123"
        )
        coEvery { beneficiaryRepository.getBeneficiaryDetails("1") } returns mockResponse

        viewModel.initState("1")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("John Doe", state.name)
        assertEquals("IBAN123", state.iban)
        assertEquals("SWIFT123", state.swift)
        assertEquals("Bank Name", state.bankName)
        assertEquals("123 Bank St", state.bankAddress)
        assertEquals(createdAt.defaultFormat(), state.createdAt)
        assertEquals(updatedAt.defaultFormat(), state.updatedAt)
        assertEquals(BeneficiaryDetailsMode.Content, state.mode)
    }

    @Test
    fun `should set error state if request fails`() = runTest {
        coEvery { beneficiaryRepository.getBeneficiaryDetails("1") } throws
                IllegalStateException()

        viewModel.initState("1")
        advanceUntilIdle()

        assertIs<BeneficiaryDetailsMode.Error>(viewModel.state.value.mode)
    }

    @Test
    fun `should delete beneficiary if request succeeds`() = runTest {
        coEvery { beneficiaryRepository.deleteBeneficiary(any()) } just Runs
        viewModel.deleteBeneficiary("1")

        assertEquals(
            expected = BeneficiaryDetailsEvent.DeleteSuccess,
            actual = viewModel.events.first()
        )
    }

    @Test
    fun `should send error event if delete beneficiary fails`() = runTest {
        coEvery { beneficiaryRepository.deleteBeneficiary(any()) } throws IllegalStateException()
        viewModel.deleteBeneficiary("1")

        assertIs<BeneficiaryDetailsEvent.DeleteError>(viewModel.events.first())
    }

    @Test
    fun `should display not found error event if get details fails with error code 403`() =
        runTest {
            coEvery { beneficiaryRepository.getBeneficiaryDetails(any()) } throws RequestError.Http(
                403,
                null,
                null
            )
            viewModel.initState("1")

            advanceUntilIdle()

            assertEquals(
                expected = BeneficiaryDetailsMode.Error(
                    type = BeneficiaryErrorType.NotFound
                ),
                actual = viewModel.state.value.mode
            )
        }

    @Test
    fun `should display not found error event if get details fails with error code 404`() =
        runTest {
            coEvery { beneficiaryRepository.getBeneficiaryDetails(any()) } throws RequestError.Http(
                403,
                null,
                null
            )
            viewModel.initState("1")

            advanceUntilIdle()

            assertEquals(
                expected = BeneficiaryDetailsMode.Error(
                    type = BeneficiaryErrorType.NotFound
                ),
                actual = viewModel.state.value.mode
            )
        }

    @Test
    fun `should display not found error event if get details fails with unmapped error code`() =
        runTest {
            coEvery { beneficiaryRepository.getBeneficiaryDetails(any()) } throws RequestError.Http(
                400,
                null,
                null
            )
            viewModel.initState("1")

            advanceUntilIdle()

            assertEquals(
                expected = BeneficiaryDetailsMode.Error(
                    type = BeneficiaryErrorType.Generic
                ),
                actual = viewModel.state.value.mode
            )
        }
}