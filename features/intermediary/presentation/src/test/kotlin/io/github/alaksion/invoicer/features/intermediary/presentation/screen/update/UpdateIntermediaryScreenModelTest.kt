package io.github.alaksion.invoicer.features.intermediary.presentation.screen.update

import io.github.alaksion.invoicer.features.intermediary.presentation.fakes.FakeIntermediaryRepository
import io.github.alaksion.invoicer.features.intermediary.presentation.fakes.FakeIntermediaryRepository.Companion.DEFAULT_INTERMEDIARY
import io.github.alaksion.invoicer.foundation.watchers.RefreshIntermediaryPublisher
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
class UpdateIntermediaryScreenModelTest {

    private lateinit var intermediaryRepository: FakeIntermediaryRepository
    private val refreshIntermediaryPublisher = RefreshIntermediaryPublisher()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: UpdateIntermediaryScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        intermediaryRepository = FakeIntermediaryRepository()
        viewModel = UpdateIntermediaryScreenModel(
            intermediaryRepository = intermediaryRepository,
            dispatcher = dispatcher,
            refreshIntermediaryPublisher = refreshIntermediaryPublisher
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
        assertEquals(DEFAULT_INTERMEDIARY.name, state.name)
        assertEquals(DEFAULT_INTERMEDIARY.iban, state.iban)
        assertEquals(DEFAULT_INTERMEDIARY.swift, state.swift)
        assertEquals(DEFAULT_INTERMEDIARY.bankName, state.bankName)
        assertEquals(DEFAULT_INTERMEDIARY.bankAddress, state.bankAddress)
        assertEquals(UpdateIntermediaryMode.Content, state.mode)
    }

    @Test
    fun `should handle error during state initialization`() = runTest {
        intermediaryRepository.detailsError = IllegalStateException()

        viewModel.initState("1")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UpdateIntermediaryMode.Error, state.mode)
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
        viewModel.updateName("John Doe")
        viewModel.updateBankName("Bank Name")
        viewModel.updateBankAddress("123 Bank St")
        viewModel.updateSwift("SWIFT123")
        viewModel.updateIban("IBAN123")

        viewModel.submit("1")

        assertEquals(UpdateIntermediaryEvent.Success, viewModel.events.first())
    }

    @Test
    fun `should handle error during submission`() = runTest {
        intermediaryRepository.updateFails = true

        viewModel.submit("1")

        assertEquals(
            UpdateIntermediaryEvent.Error(""),
            viewModel.events.first()
        )
    }
}
