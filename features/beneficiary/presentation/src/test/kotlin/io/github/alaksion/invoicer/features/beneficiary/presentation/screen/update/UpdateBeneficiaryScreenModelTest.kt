package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update

import foundation.network.RequestError
import foundation.watchers.RefreshBeneficiaryPublisher
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
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

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateBeneficiaryScreenModelTest {

    private val beneficiaryRepository = mockk<BeneficiaryRepository>()
    private val refreshBeneficiaryPublisher = RefreshBeneficiaryPublisher()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: UpdateBeneficiaryScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        viewModel = UpdateBeneficiaryScreenModel(
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
        val mockResponse = BeneficiaryModel(
            name = "John Doe",
            iban = "IBAN123",
            swift = "SWIFT123",
            bankName = "Bank Name",
            bankAddress = "123 Bank St",
            id = "1",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-02T00:00:00Z")
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
        assertEquals(UpdateBeneficiaryMode.Content, state.mode)
    }

    @Test
    fun `should handle error during state initialization`() = runTest {
        coEvery { beneficiaryRepository.getBeneficiaryDetails("1") } throws RequestError.Http(
            404,
            null,
            "Not Found"
        )

        viewModel.initState("1")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UpdateBeneficiaryMode.Error, state.mode)
    }

    @Test
    fun `should update name`() {
        viewModel.updateName("Jane Doe")
        assertEquals("Jane Doe", viewModel.state.value.name)
    }

    @Test
    fun `should update bank name`() {
        viewModel.updateBankName("New Bank Name")
        assertEquals("New Bank Name", viewModel.state.value.bankName)
    }

    @Test
    fun `should update bank address`() {
        viewModel.updateBankAddress("456 New Bank St")
        assertEquals("456 New Bank St", viewModel.state.value.bankAddress)
    }

    @Test
    fun `should update swift`() {
        viewModel.updateSwift("NEW123SWIFT")
        assertEquals("NEW123SWIFT", viewModel.state.value.swift)
    }

    @Test
    fun `should update iban`() {
        viewModel.updateIban("NEWIBAN123456")
        assertEquals("NEWIBAN123456", viewModel.state.value.iban)
    }

    @Test
    fun `should submit successfully`() = runTest {
        coEvery {
            beneficiaryRepository.updateBeneficiary(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } just Runs

        viewModel.updateName("John Doe")
        viewModel.updateBankName("Bank Name")
        viewModel.updateBankAddress("123 Bank St")
        viewModel.updateSwift("SWIFT123")
        viewModel.updateIban("IBAN123")

        viewModel.submit("1")

        assertEquals(UpdateBeneficiaryEvent.Success, viewModel.events.first())
    }

    @Test
    fun `should handle error during submission`() = runTest {
        coEvery {
            beneficiaryRepository.updateBeneficiary(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } throws RequestError.Other(
            Exception("")
        )

        viewModel.submit("1")

        assertEquals(
            UpdateBeneficiaryEvent.Error(""),
            viewModel.events.first()
        )
    }
}
