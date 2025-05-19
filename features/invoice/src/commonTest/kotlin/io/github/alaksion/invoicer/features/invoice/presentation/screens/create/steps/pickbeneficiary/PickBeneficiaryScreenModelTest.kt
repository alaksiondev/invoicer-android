package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary

import io.github.alaksion.invoicer.features.invoice.presentation.fakes.FakeBeneficiaryRepository
import io.github.alaksion.invoicer.features.invoice.presentation.fakes.FakeDateProvider
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
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

@OptIn(ExperimentalCoroutinesApi::class)
class PickBeneficiaryScreenModelTest {

    private lateinit var createInvoiceManager: CreateInvoiceManager
    private lateinit var beneficiaryRepository: FakeBeneficiaryRepository
    private lateinit var dateProvider: FakeDateProvider
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: PickBeneficiaryScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        dateProvider = FakeDateProvider()
        createInvoiceManager = CreateInvoiceManager(dateProvider)
        beneficiaryRepository = FakeBeneficiaryRepository()
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
        assertEquals(FakeBeneficiaryRepository.DEFAULT_BENEFICIARIES.items, state.beneficiaries)
    }

    @Test
    fun `should set  state to error if load beneficiaries fails`() = runTest {
        beneficiaryRepository.beneficiariesFails = true

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

        viewModel.updateSelection(BeneficiarySelection.Existing(FakeBeneficiaryRepository.DEFAULT_BENEFICIARY.id))
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
}
