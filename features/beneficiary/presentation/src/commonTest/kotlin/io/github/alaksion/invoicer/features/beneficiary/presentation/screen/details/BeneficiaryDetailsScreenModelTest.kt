package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details

import io.github.alaksion.invoicer.features.beneficiary.presentation.fakes.FakeBeneficiaryRepository
import io.github.alaksion.invoicer.features.beneficiary.presentation.fakes.FakeBeneficiaryRepository.Companion.DEFAULT_BENEFICIARY
import io.github.alaksion.invoicer.foundation.network.RequestError
import io.github.alaksion.invoicer.foundation.utils.date.defaultFormat
import io.github.alaksion.invoicer.foundation.watchers.RefreshBeneficiaryPublisher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class BeneficiaryDetailsScreenModelTest {

    private lateinit var beneficiaryRepository: FakeBeneficiaryRepository
    private val refreshBeneficiaryPublisher = RefreshBeneficiaryPublisher()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: BeneficiaryDetailsScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        beneficiaryRepository = FakeBeneficiaryRepository()
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
        viewModel.initState("1")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(DEFAULT_BENEFICIARY.name, state.name)
        assertEquals(DEFAULT_BENEFICIARY.iban, state.iban)
        assertEquals(DEFAULT_BENEFICIARY.swift, state.swift)
        assertEquals(DEFAULT_BENEFICIARY.bankName, state.bankName)
        assertEquals(DEFAULT_BENEFICIARY.bankAddress, state.bankAddress)
        assertEquals(DEFAULT_BENEFICIARY.createdAt.defaultFormat(), state.createdAt)
        assertEquals(DEFAULT_BENEFICIARY.updatedAt.defaultFormat(), state.updatedAt)
        assertEquals(BeneficiaryDetailsMode.Content, state.mode)
    }

    @Test
    fun `should set error state if request fails`() = runTest {
        beneficiaryRepository.detailsError = IllegalStateException()

        viewModel.initState("1")
        advanceUntilIdle()

        assertIs<BeneficiaryDetailsMode.Error>(viewModel.state.value.mode)
    }

    @Test
    fun `should delete beneficiary if request succeeds`() = runTest {
        viewModel.deleteBeneficiary("1")

        assertEquals(
            expected = BeneficiaryDetailsEvent.DeleteSuccess,
            actual = viewModel.events.first()
        )
    }

    @Test
    fun `should send error event if delete beneficiary fails`() = runTest {
        beneficiaryRepository.deleteFails = true
        viewModel.deleteBeneficiary("1")

        assertIs<BeneficiaryDetailsEvent.DeleteError>(viewModel.events.first())
    }

    @Test
    fun `should display not found error event if get details fails with error code 403`() =
        runTest {
            beneficiaryRepository.detailsError = RequestError.Http(
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
            beneficiaryRepository.detailsError = RequestError.Http(
                404,
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
    fun `should display not found error if get details fails with unmapped error code`() =
        runTest {
            beneficiaryRepository.detailsError = RequestError.Http(
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
