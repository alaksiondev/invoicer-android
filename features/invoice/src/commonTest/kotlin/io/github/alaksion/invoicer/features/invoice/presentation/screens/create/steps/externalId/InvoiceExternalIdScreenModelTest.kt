package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId

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
class InvoiceExternalIdScreenModelTest {

    private lateinit var manager: CreateInvoiceManager
    private lateinit var dateProvider: FakeDateProvider
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: InvoiceExternalIdScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        dateProvider = FakeDateProvider()
        manager = CreateInvoiceManager(dateProvider)
        viewModel = InvoiceExternalIdScreenModel(manager, dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should initialize state with externalId from manager`() = runTest {
        manager.externalId = "12345"

        viewModel.initState()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("12345", state.externalId)
    }

    @Test
    fun `should update externalId in state`() {
        viewModel.updateExternalId("67890")
        assertEquals("67890", viewModel.state.value.externalId)
    }

    @Test
    fun `should submit externalId to manager and emit event`() = runTest {
        viewModel.updateExternalId("67890")

        viewModel.submit()

        val event = viewModel.events.first()

        assertEquals(Unit, event)
        assertEquals(
            expected = "67890",
            actual = manager.externalId
        )
    }
}
