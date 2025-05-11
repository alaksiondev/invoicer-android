package io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state

import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceList
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceListItem
import io.github.alaksion.invoicer.features.invoice.domain.repository.InvoiceRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class InvoiceListScreenModelTest {

    private val invoiceRepository = mockk<InvoiceRepository>()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: InvoiceListScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
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
        val mockResponse = InvoiceList(
            items = listOf(
                invoiceItem.copy(
                    id = "1",
                    externalId = "EXT1"
                ),
                invoiceItem.copy(
                    id = "2",
                    externalId = "EXT2"
                ),
            ),
            totalItemCount = 2,
            nextPage = null
        )
        coEvery {
            invoiceRepository.getInvoices(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns mockResponse

        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(InvoiceListMode.Content, state.mode)
        assertEquals(mockResponse.items, state.invoices)
        assertTrue(viewModel.state.value.isLoadingMore.not())
    }

    @Test
    fun `should set error state if load page fails`() = runTest {

        coEvery {
            invoiceRepository.getInvoices(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } throws IllegalStateException()

        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(InvoiceListMode.Error, state.mode)
    }

    @Test
    fun `should load next page successfully`() = runTest {
        val firstPageResponse = InvoiceList(
            items = listOf(
                invoiceItem.copy(
                    id = "1",
                    externalId = "EXT1"
                ),
                invoiceItem.copy(
                    id = "2",
                    externalId = "EXT2"
                ),
            ),
            totalItemCount = 2,
            nextPage = 1
        )
        val nextPageResponse = InvoiceList(
            items = listOf(
                invoiceItem.copy(
                    id = "3",
                    externalId = "EXT3"
                ),
                invoiceItem.copy(
                    id = "4",
                    externalId = "EXT4"
                ),
            ),
            totalItemCount = 2,
            nextPage = 2
        )
        coEvery {
            invoiceRepository.getInvoices(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns firstPageResponse andThen nextPageResponse

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
            firstPageResponse.items + nextPageResponse.items,
            state.invoices
        )
    }

    @Test
    fun `should send error event if load next page fails`() = runTest {
        val firstPageResponse = InvoiceList(
            items = listOf(
                invoiceItem.copy(
                    id = "1",
                    externalId = "EXT1"
                ),
                invoiceItem.copy(
                    id = "2",
                    externalId = "EXT2"
                ),
            ),
            totalItemCount = 2,
            nextPage = 1
        )

        coEvery {
            invoiceRepository.getInvoices(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns firstPageResponse andThenThrows IllegalStateException()

        viewModel.loadPage()
        advanceUntilIdle()
        viewModel.nextPage()


        assertEquals(
            expected = InvoiceListEvent.Error(""),
            actual = viewModel.events.first()
        )
    }

    companion object {
        val invoiceItem = InvoiceListItem(
            id = "0",
            externalId = "EXT0",
            senderCompany = "Sender D",
            recipientCompany = "Recipient D",
            issueDate = Instant.parse("2023-01-04T00:00:00Z"),
            dueDate = Instant.parse("2023-01-18T00:00:00Z"),
            createdAt = Instant.parse("2023-01-04T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-05T00:00:00Z"),
            totalAmount = 4000L
        )
    }
}
