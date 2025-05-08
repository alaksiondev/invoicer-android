package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary

import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiariesModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import io.github.alaksion.invoicer.foundation.utils.date.DateProvider
import io.mockk.coEvery
import io.mockk.every
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
class PickBeneficiaryScreenModelTest {

    private lateinit var createInvoiceManager: CreateInvoiceManager
    private val beneficiaryRepository: BeneficiaryRepository = mockk()
    private val dateProvider: DateProvider = mockk()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: PickBeneficiaryScreenModel

    private fun defaultMocks() {
        every { dateProvider.get() } returns Instant.parse("2023-10-01T12:00:00Z")
        coEvery { beneficiaryRepository.getBeneficiaries(any(), any()) }.returns(
            beneficiariesResponse
        )
        createInvoiceManager = CreateInvoiceManager(dateProvider)
    }

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        defaultMocks()
        viewModel =
            PickBeneficiaryScreenModel(
                createInvoiceManager,
                beneficiaryRepository,
                dispatcher
            )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should initialize state with beneficiaries`() = runTest {
        viewModel.initState()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(PickBeneficiaryUiMode.Content, state.uiMode)
        assertEquals(beneficiariesResponse.items, state.beneficiaries)
    }

    @Test
    fun `should set  state to error if load beneficiaries fails`() = runTest {
        coEvery { beneficiaryRepository.getBeneficiaries(any(), any()) } throws Exception()
        viewModel.initState()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(PickBeneficiaryUiMode.Error, state.uiMode)
    }

    @Test
    fun `should update selection to existing beneficiary`() = runTest {
        viewModel.initState()
        advanceUntilIdle()

        viewModel.updateSelection(BeneficiarySelection.Existing("1"))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(BeneficiarySelection.Existing("1"), state.selection)
    }

    @Test
    fun `should submit existing beneficiary`() = runTest {
        viewModel.initState()
        advanceUntilIdle()

        viewModel.updateSelection(BeneficiarySelection.Existing(beneficiaryStub.id))
        advanceUntilIdle()

        viewModel.submit()

        val event = viewModel.events.first()

        assertEquals(PickBeneficiaryEvents.Continue, event)

        assertEquals(
            expected = "1",
            actual = createInvoiceManager.beneficiaryId
        )
        assertEquals(
            expected = "John Doe",
            actual = createInvoiceManager.beneficiaryName
        )
    }

    @Test
    fun `should emit event to start new beneficiary`() = runTest {
        viewModel.initState()
        advanceUntilIdle()

        viewModel.updateSelection(BeneficiarySelection.New)
        advanceUntilIdle()

        viewModel.submit()

        val event = viewModel.events.first()
        assertEquals(PickBeneficiaryEvents.StartNewBeneficiary, event)
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

        val beneficiariesResponse = BeneficiariesModel(
            items = beneficiaries,
            nextPage = 2,
            itemCount = 2
        )
    }
}