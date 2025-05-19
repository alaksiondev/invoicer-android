package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list

import io.github.alaksion.invoicer.features.beneficiary.presentation.fakes.FakeBeneficiaryRepository
import io.github.alaksion.invoicer.features.beneficiary.presentation.fakes.FakeBeneficiaryRepository.Companion.DEFAULT_BENEFICIARIES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class BeneficiaryListScreenModelTest {

    private lateinit var beneficiaryRepository: FakeBeneficiaryRepository
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: BeneficiaryListScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        beneficiaryRepository = FakeBeneficiaryRepository()
        viewModel = BeneficiaryListScreenModel(
            beneficiaryRepository = beneficiaryRepository,
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
        assertEquals(BeneficiaryListMode.Content, state.mode)
        assertEquals(DEFAULT_BENEFICIARIES.items, state.beneficiaries)
        assertTrue(viewModel.state.value.isNextPageLoading.not())
    }

    @Test
    fun `should handle error when loading first page`() = runTest {
        beneficiaryRepository.beneficiariesFails = true

        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(BeneficiaryListMode.Error, state.mode)
    }
}
