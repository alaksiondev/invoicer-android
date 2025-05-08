package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company

import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import io.github.alaksion.invoicer.foundation.utils.date.DateProvider
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Instant
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class InvoiceCompanyScreenModelTest {

    private lateinit var manager: CreateInvoiceManager
    private val dateProvider: DateProvider = mockk()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: InvoiceCompanyScreenModel

    private fun defaultMocks() {
        every { dateProvider.get() } returns Instant.parse("2023-10-01T12:00:00Z")
        manager = CreateInvoiceManager(dateProvider)
    }

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        defaultMocks()
        viewModel = InvoiceCompanyScreenModel(manager, dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should update sender name`() {
        viewModel.onSenderNameChange("Sender Name")
        assertEquals("Sender Name", viewModel.state.value.senderName)
    }

    @Test
    fun `should update sender address`() {
        viewModel.onSenderAddressChange("Sender Address")
        assertEquals("Sender Address", viewModel.state.value.senderAddress)
    }

    @Test
    fun `should update recipient name`() {
        viewModel.onRecipientNameChange("Recipient Name")
        assertEquals("Recipient Name", viewModel.state.value.recipientName)
    }

    @Test
    fun `should update recipient address`() {
        viewModel.onRecipientAddressChange("Recipient Address")
        assertEquals("Recipient Address", viewModel.state.value.recipientAddress)
    }

    @Test
    fun `should submit data to manager and emit event`() = runTest {
        viewModel.onSenderNameChange("Sender Name")
        viewModel.onSenderAddressChange("Sender Address")
        viewModel.onRecipientNameChange("Recipient Name")
        viewModel.onRecipientAddressChange("Recipient Address")

        viewModel.submit()

        val event = viewModel.events.first()
        assertEquals(Unit, event)
        assertEquals("Sender Name", manager.senderCompanyName)
        assertEquals("Sender Address", manager.senderCompanyAddress)
        assertEquals("Recipient Name", manager.recipientCompanyName)
        assertEquals("Recipient Address", manager.recipientCompanyAddress)
    }
}