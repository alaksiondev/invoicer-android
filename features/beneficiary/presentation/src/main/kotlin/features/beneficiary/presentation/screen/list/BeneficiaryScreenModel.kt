package features.beneficiary.presentation.screen.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiariesModel
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
import foundation.network.request.handle
import foundation.network.request.launchRequest
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class BeneficiaryScreenModel(
    private val beneficiaryRepository: BeneficiaryRepository,
    private val dispatcher: CoroutineDispatcher,
) : ScreenModel, foundation.ui.events.EventAware<BeneficiaryListEvents> by foundation.ui.events.EventPublisher() {

    private var page: Long = 0
    private var isFirstPageLoaded = false
    private var paginationEnabled = false

    private val _state = MutableStateFlow(BeneficiaryListState())
    val state: StateFlow<BeneficiaryListState> = _state

    fun loadPage(
        force: Boolean = false
    ) {
        if (isFirstPageLoaded.not() || force) {
            screenModelScope.launch(dispatcher) {
                launchRequest { getBeneficiaries() }
                    .handle(
                        onStart = {
                            _state.value = _state.value.copy(
                                mode = BeneficiaryListMode.Loading,
                            )
                        },
                        onSuccess = {
                            paginationEnabled = it.nextPage != null

                            _state.value = _state.value.copy(
                                beneficiaries = it.items.toPersistentList(),
                                mode = BeneficiaryListMode.Content
                            )
                            it.nextPage?.let { nextPage ->
                                page = nextPage
                            }
                            isFirstPageLoaded = true
                        },
                        onFinish = { /* no op */ },
                        onFailure = {
                            _state.value = _state.value.copy(
                                mode = BeneficiaryListMode.Error
                            )
                        }
                    )
            }
        }
    }

    fun nextPage() {
        if (isFirstPageLoaded && _state.value.isNextPageLoading.not() && paginationEnabled) {
            screenModelScope.launch(dispatcher) {
                launchRequest { getBeneficiaries() }
                    .handle(
                        onStart = {
                            _state.value = _state.value.copy(
                                isNextPageLoading = true
                            )
                        },
                        onFinish = {
                            _state.value = _state.value.copy(
                                isNextPageLoading = false
                            )
                        },
                        onFailure = {
                            publish(BeneficiaryListEvents.LoadMoreError)
                        },
                        onSuccess = { result ->
                            paginationEnabled = result.nextPage != null
                            result.nextPage?.let {
                                page = it
                            }

                            _state.update { oldState ->
                                oldState.copy(
                                    beneficiaries = (oldState.beneficiaries + result.items)
                                        .toPersistentList()
                                )
                            }
                        },
                    )
            }
        }
    }

    private suspend fun getBeneficiaries(): BeneficiariesModel =
        beneficiaryRepository.getBeneficiaries(
            page = page,
            limit = PAGE_LIMIT,
        )

    companion object {
        const val PAGE_LIMIT = 20L
    }

}