package io.github.alaksion.invoicer.features.intermediary.presentation.screen.list

import io.github.alaksion.invoicer.features.intermediary.presentation.fakes.FakeIntermediaryRepository
import io.github.alaksion.invoicer.features.intermediary.presentation.fakes.FakeIntermediaryRepository.Companion.DEFAULT_INTERMEDIARIES
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.IntermediaryListMode
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.IntermediaryListScreenModel
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
class IntermediaryListScreenModelTest {

    private lateinit var intermediaryRepository: FakeIntermediaryRepository
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: IntermediaryListScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        intermediaryRepository = FakeIntermediaryRepository()
        viewModel = IntermediaryListScreenModel(
            intermediaryRepository = intermediaryRepository,
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
        assertEquals(IntermediaryListMode.Content, state.mode)
        assertEquals(DEFAULT_INTERMEDIARIES, state.beneficiaries)
        assertTrue(viewModel.state.value.isNextPageLoading.not())
    }

    @Test
    fun `should handle error when loading first page`() = runTest {
        intermediaryRepository.listFails = true

        viewModel.loadPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(IntermediaryListMode.Error, state.mode)
    }
}
