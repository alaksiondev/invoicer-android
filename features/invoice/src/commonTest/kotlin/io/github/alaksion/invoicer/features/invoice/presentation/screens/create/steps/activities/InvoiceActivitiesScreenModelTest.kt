package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities

import io.github.alaksion.invoicer.features.invoice.presentation.fakes.FakeDateProvider
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class InvoiceActivitiesScreenModelTest {

    private lateinit var createInvoiceManager: CreateInvoiceManager
    private lateinit var dateProvider: FakeDateProvider
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: InvoiceActivitiesScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        dateProvider = FakeDateProvider()
        createInvoiceManager = CreateInvoiceManager(dateProvider)
        viewModel = InvoiceActivitiesScreenModel(
            dispatcher = dispatcher,
            createInvoiceManager = createInvoiceManager
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should initialize state with activities`() = runTest {
        val mockActivities = listOf(
            CreateInvoiceActivityUiModel("Description 1", 100, 2),
            CreateInvoiceActivityUiModel("Description 2", 200, 4),
        )
        createInvoiceManager.activities.addAll(mockActivities)

        viewModel.initState()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(mockActivities, state.activities)
    }

    @Test
    fun `should update form description`() {
        viewModel.updateFormDescription("New Description")
        assertEquals("New Description", viewModel.state.value.formState.description)
    }

    @Test
    fun `should update form quantity`() {
        viewModel.updateFormQuantity("5")
        assertEquals("5", viewModel.state.value.formState.quantity)
    }

    @Test
    fun `should update form unit price`() {
        viewModel.updateFormUnitPrice("150")
        assertEquals("150", viewModel.state.value.formState.unitPrice)
    }

    @Test
    fun `should add activity successfully`() = runTest {
        val mockActivity = CreateInvoiceActivityUiModel("Description 1", 100, 2)
        viewModel.updateFormDescription(mockActivity.description)
        viewModel.updateFormQuantity(mockActivity.quantity.toString())
        viewModel.updateFormUnitPrice(mockActivity.unitPrice.toString())

        viewModel.addActivity()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state.activities.contains(mockActivity))
    }

    @Test
    fun `should emit error when adding activity with invalid unit price`() = runTest {
        viewModel.updateFormUnitPrice("0")
        viewModel.updateFormQuantity("5")

        viewModel.addActivity()

        val event = viewModel.events.first()
        assertEquals(InvoiceActivitiesEvent.ActivityUnitPriceError, event)
    }

    @Test
    fun `should emit error when adding activity with invalid quantity`() = runTest {
        viewModel.updateFormUnitPrice("100")
        viewModel.updateFormQuantity("0")

        viewModel.addActivity()

        val event = viewModel.events.first()
        assertEquals(InvoiceActivitiesEvent.ActivityQuantityError, event)
    }

    @Test
    fun `should remove activity successfully`() = runTest {
        val mockActivity = CreateInvoiceActivityUiModel("Description 1", 100, 2)
        createInvoiceManager.activities.add(mockActivity)

        viewModel.removeActivity(mockActivity.id)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state.activities.isEmpty())
    }
}
