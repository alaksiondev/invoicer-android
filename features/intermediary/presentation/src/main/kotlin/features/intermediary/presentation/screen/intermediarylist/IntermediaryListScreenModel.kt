package features.intermediary.presentation.screen.intermediarylist

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.features.intermediary.services.domain.model.IntermediaryModel
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository
import foundation.network.request.handle
import foundation.network.request.launchRequest
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class IntermediaryListScreenModel(
    private val intermediaryRepository: IntermediaryRepository,
    private val dispatcher: CoroutineDispatcher,
) : ScreenModel {

    private var isInitialized = false

    private val _state = MutableStateFlow(IntermediaryListState())
    val state: StateFlow<IntermediaryListState> = _state

    fun loadPage(
        force: Boolean = false
    ) {
        if (isInitialized.not() || force) {
            screenModelScope.launch(dispatcher) {
                launchRequest { getIntermediaries() }
                    .handle(
                        onStart = {
                            _state.value = _state.value.copy(
                                mode = IntermediaryListMode.Loading,
                            )
                        },
                        onSuccess = {
                            _state.value = _state.value.copy(
                                beneficiaries = it.toPersistentList(),
                                mode = IntermediaryListMode.Content
                            )
                            isInitialized = true
                        },
                        onFailure = {
                            _state.value = _state.value.copy(
                                mode = IntermediaryListMode.Error
                            )
                        }
                    )
            }
        }
    }

    private suspend fun getIntermediaries(): List<IntermediaryModel> =
        // No pagination support for this feature
        intermediaryRepository.getIntermediaries(
            page = 0,
            limit = PAGE_LIMIT,
        )

    companion object {
        const val PAGE_LIMIT = 1000L
    }
}