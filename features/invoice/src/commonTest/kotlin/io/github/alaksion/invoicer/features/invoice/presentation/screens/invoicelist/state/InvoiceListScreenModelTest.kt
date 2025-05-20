package io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state

import io.github.alaksion.invoicer.features.invoice.presentation.fakes.FakeInvoiceRepository
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
class InvoiceListScreenModelTest {

    private lateinit var invoiceRepository: FakeInvoiceRepository
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: InvoiceListScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        invoiceRepository = FakeInvoiceRepository()
        viewModel = InvoiceListScreenModel(
            invoiceRepository = invoiceRepository,
            dispatcher = dispatcher
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load first page successfully`() = runTest {
        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(InvoiceListMode.Content, state.mode)
        assertEquals(FakeInvoiceRepository.INVOICE_LIST, state.invoices)
        assertTrue(viewModel.state.value.isLoadingMore.not())
    }

    @Test
    fun `should set error state if load page fails`() = runTest {
        invoiceRepository.getInvoicesFails = true
        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(InvoiceListMode.Error, state.mode)
    }

    @Test
    fun `should load next page successfully`() = runTest {

        viewModel.loadPage()
        advanceUntilIdle()
        viewModel.nextPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(InvoiceListMode.Content, state.mode)

        assertEquals(
            actual = state.invoices.size,
            expected = 4
        )
        assertEquals(
            FakeInvoiceRepository.INVOICE_LIST + FakeInvoiceRepository.INVOICE_LIST,
            state.invoices
        )
    }

    @Test
    fun `should send error event if load next page fails`() = runTest {
        viewModel.loadPage()
        advanceUntilIdle()

        invoiceRepository.getInvoicesFails = true
        viewModel.nextPage()
        assertEquals(
            expected = InvoiceListEvent.Error(""),
            actual = viewModel.events.first()
        )
    }
}
