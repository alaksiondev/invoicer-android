package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update

import io.github.alaksion.invoicer.features.beneficiary.presentation.fakes.FakeBeneficiaryRepository
import io.github.alaksion.invoicer.features.beneficiary.presentation.fakes.FakeBeneficiaryRepository.Companion.DEFAULT_BENEFICIARY
import io.github.alaksion.invoicer.foundation.watchers.RefreshBeneficiaryPublisher
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
class UpdateBeneficiaryScreenModelTest {

    private lateinit var beneficiaryRepository: FakeBeneficiaryRepository
    private val refreshBeneficiaryPublisher = RefreshBeneficiaryPublisher()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: UpdateBeneficiaryScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        beneficiaryRepository = FakeBeneficiaryRepository()
        viewModel = UpdateBeneficiaryScreenModel(
            beneficiaryRepository = beneficiaryRepository,
            dispatcher = dispatcher,
            refreshBeneficiaryPublisher = refreshBeneficiaryPublisher
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
        assertEquals(DEFAULT_BENEFICIARY.name, state.name)
        assertEquals(DEFAULT_BENEFICIARY.iban, state.iban)
        assertEquals(DEFAULT_BENEFICIARY.swift, state.swift)
        assertEquals(DEFAULT_BENEFICIARY.bankName, state.bankName)
        assertEquals(DEFAULT_BENEFICIARY.bankAddress, state.bankAddress)
        assertEquals(UpdateBeneficiaryMode.Content, state.mode)
    }

    @Test
    fun `should handle error during state initialization`() = runTest {
        beneficiaryRepository.detailsError = IllegalStateException()

        viewModel.initState("1")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UpdateBeneficiaryMode.Error, state.mode)
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

        assertEquals(UpdateBeneficiaryEvent.Success, viewModel.events.first())
    }

    @Test
    fun `should handle error during submission`() = runTest {
        beneficiaryRepository.updateFails = true

        viewModel.submit("1")

        assertEquals(
            UpdateBeneficiaryEvent.Error(""),
            viewModel.events.first()
        )
    }
}
