package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary

import io.github.alaksion.invoicer.features.intermediary.services.domain.model.IntermediaryModel
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository
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
class PickIntermediaryScreenModelTest {

    private lateinit var createInvoiceManager: CreateInvoiceManager
    private val intermediaryScreenModel: IntermediaryRepository = mockk()
    private val dateProvider: DateProvider = mockk()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: PickIntermediaryScreenModel

    private fun defaultMocks() {
        every { dateProvider.get() } returns Instant.parse("2023-10-01T12:00:00Z")
        coEvery { intermediaryScreenModel.getIntermediaries(any(), any()) }.returns(intermediaries)
        createInvoiceManager = CreateInvoiceManager(dateProvider)
    }

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        defaultMocks()
        viewModel =
            PickIntermediaryScreenModel(
                createInvoiceManager,
                intermediaryScreenModel,
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
        assertEquals(intermediaries, state.intermediaries)
    }

    @Test
    fun `should set  state to error if load intermediaries fails`() = runTest {
        coEvery { intermediaryScreenModel.getIntermediaries(any(), any()) } throws Exception()
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

        viewModel.updateSelection(IntermediarySelection.Existing(intermediaryStub.id))
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