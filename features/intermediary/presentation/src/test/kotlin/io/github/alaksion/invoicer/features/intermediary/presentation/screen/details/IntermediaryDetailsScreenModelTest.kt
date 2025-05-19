package io.github.alaksion.invoicer.features.intermediary.presentation.screen.details

import io.github.alaksion.invoicer.features.intermediary.presentation.fakes.FakeIntermediaryRepository
import io.github.alaksion.invoicer.features.intermediary.presentation.fakes.FakeIntermediaryRepository.Companion.DEFAULT_INTERMEDIARY
import io.github.alaksion.invoicer.foundation.network.RequestError
import io.github.alaksion.invoicer.foundation.utils.date.defaultFormat
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
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class IntermediaryDetailsScreenModelTest {

    private lateinit var intermediaryRepository: FakeIntermediaryRepository
    private val refreshIntermediaryPublisher = RefreshIntermediaryPublisher()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: IntermediaryDetailsScreenModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        intermediaryRepository = FakeIntermediaryRepository()
        viewModel = IntermediaryDetailsScreenModel(
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
        assertEquals(DEFAULT_INTERMEDIARY.createdAt.defaultFormat(), state.createdAt)
        assertEquals(DEFAULT_INTERMEDIARY.updatedAt.defaultFormat(), state.updatedAt)
        assertEquals(IntermediaryDetailsMode.Content, state.mode)
    }

    @Test
    fun `should set error state if request fails`() = runTest {
        intermediaryRepository.detailsError = IllegalStateException()
        viewModel.initState("1")
        advanceUntilIdle()

        assertIs<IntermediaryDetailsMode.Error>(viewModel.state.value.mode)
    }

    @Test
    fun `should delete intermediary if request succeeds`() = runTest {
        viewModel.deleteIntermediary("1")

        assertEquals(
            expected = IntermediaryDetailsEvent.DeleteSuccess,
            actual = viewModel.events.first()
        )
    }

    @Test
    fun `should send error event if delete intermediary fails`() = runTest {
        intermediaryRepository.deleteFails = true
        viewModel.deleteIntermediary("1")

        assertIs<IntermediaryDetailsEvent.DeleteError>(viewModel.events.first())
    }

    @Test
    fun `should display not found error event if get details fails with error code 403`() =
        runTest {
            intermediaryRepository.detailsError = RequestError.Http(
                403,
                null,
                null
            )
            viewModel.initState("1")

            advanceUntilIdle()

            assertEquals(
                expected = IntermediaryDetailsMode.Error(
                    type = IntermediaryErrorType.NotFound
                ),
                actual = viewModel.state.value.mode
            )
        }

    @Test
    fun `should display not found error event if get details fails with error code 404`() =
        runTest {
            intermediaryRepository.detailsError = RequestError.Http(
                404,
                null,
                null
            )
            viewModel.initState("1")

            advanceUntilIdle()

            assertEquals(
                expected = IntermediaryDetailsMode.Error(
                    type = IntermediaryErrorType.NotFound
                ),
                actual = viewModel.state.value.mode
            )
        }

    @Test
    fun `should display not found error event if get details fails with unmapped error code`() =
        runTest {
            intermediaryRepository.detailsError = RequestError.Http(
                400,
                null,
                null
            )
            viewModel.initState("1")

            advanceUntilIdle()

            assertEquals(
                expected = IntermediaryDetailsMode.Error(
                    type = IntermediaryErrorType.Generic
                ),
                actual = viewModel.state.value.mode
            )
        }
}
