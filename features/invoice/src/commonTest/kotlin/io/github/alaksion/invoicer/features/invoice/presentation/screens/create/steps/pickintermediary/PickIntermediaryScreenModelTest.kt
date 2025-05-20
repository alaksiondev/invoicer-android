package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary

import io.github.alaksion.invoicer.features.invoice.presentation.fakes.FakeDateProvider
import io.github.alaksion.invoicer.features.invoice.presentation.fakes.FakeIntermediaryRepository
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
class PickIntermediaryScreenModelTest {

    private lateinit var createInvoiceManager: CreateInvoiceManager
    private lateinit var intermediaryRepository: FakeIntermediaryRepository
    private lateinit var dateProvider: FakeDateProvider
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: PickIntermediaryScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        dateProvider = FakeDateProvider()
        intermediaryRepository = FakeIntermediaryRepository()
        createInvoiceManager = CreateInvoiceManager(dateProvider)
        viewModel =
            PickIntermediaryScreenModel(
                createInvoiceManager,
                intermediaryRepository,
                dispatcher
            )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should initialize state with intermediaries`() = runTest {
        viewModel.initState()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(PickIntermediaryUiMode.Content, state.uiMode)
        assertEquals(FakeIntermediaryRepository.DEFAULT_INTERMEDIARIES, state.intermediaries)
    }

    @Test
    fun `should set  state to error if load intermediaries fails`() = runTest {
        intermediaryRepository.listFails = true
        viewModel.initState()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(PickIntermediaryUiMode.Error, state.uiMode)
    }

    @Test
    fun `should update selection to existing intermediary`() = runTest {
        viewModel.initState()
        advanceUntilIdle()

        viewModel.updateSelection(IntermediarySelection.Existing("1"))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(IntermediarySelection.Existing("1"), state.selection)
    }

    @Test
    fun `should submit existing intermediary`() = runTest {
        viewModel.initState()
        advanceUntilIdle()

        viewModel.updateSelection(IntermediarySelection.Existing(FakeIntermediaryRepository.DEFAULT_INTERMEDIARY.id))
        advanceUntilIdle()

        viewModel.submit()

        val event = viewModel.events.first()

        assertEquals(PickIntermediaryEvents.Continue, event)

        assertEquals(
            expected = "1",
            actual = createInvoiceManager.intermediaryId
        )
        assertEquals(
            expected = "John Doe",
            actual = createInvoiceManager.intermediaryName
        )
    }

    @Test
    fun `should emit event to start new intermediary`() = runTest {
        viewModel.initState()
        advanceUntilIdle()

        viewModel.updateSelection(IntermediarySelection.New)
        advanceUntilIdle()

        viewModel.submit()

        val event = viewModel.events.first()
        assertEquals(PickIntermediaryEvents.StartNewIntermediary, event)
    }

    @Test
    fun `should emit event to continue without intermediary`() = runTest {
        viewModel.initState()
        advanceUntilIdle()

        viewModel.updateSelection(IntermediarySelection.Ignore)
        advanceUntilIdle()

        viewModel.submit()

        val event = viewModel.events.first()
        assertEquals(PickIntermediaryEvents.Continue, event)
    }
}
