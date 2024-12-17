package features.invoice.presentation.screens.invoicelist

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.invoice.domain.model.InvoiceListItem
import features.invoice.domain.repository.InvoiceRepository
import foundation.events.EventAware
import foundation.events.EventPublisher
import foundation.network.request.handle
import foundation.network.request.launchRequest
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class InvoiceListScreenModel(
    private val invoiceRepository: InvoiceRepository,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel, EventAware<InvoiceListEvent> by EventPublisher() {

    private val _state = MutableStateFlow(InvoiceListState())
    private var page = 0
    private var isFirstPageLoaded = false

    val state: StateFlow<InvoiceListState> get() = _state

    fun loadPage() {
        if (isFirstPageLoaded.not()) {
            screenModelScope.launch(dispatcher) {
                launchRequest { getInvoices() }
                    .handle(
                        onStart = {
                            _state.value = _state.value.copy(
                                isLoading = true,
                                showError = false
                            )
                        },
                        onSuccess = {
                            _state.value = _state.value.copy(
                                invoices = (_state.value.invoices + it).toPersistentList()
                            )
                            page++
                            isFirstPageLoaded = true
                        },
                        onFinish = {
                            _state.value = _state.value.copy(
                                isLoading = false
                            )
                        },
                        onFailure = {
                            it.message
                            _state.value = _state.value.copy(
                                showError = true
                            )
                        }
                    )
            }
        }
    }

    fun nextPage() {
        if (isFirstPageLoaded && _state.value.isLoadingMore.not()) {
            screenModelScope.launch(dispatcher) {
                launchRequest { getInvoices() }
                    .handle(
                        onStart = {
                            _state.value = _state.value.copy(
                                isLoadingMore = true
                            )
                        },
                        onFinish = {
                            _state.value = _state.value.copy(
                                isLoadingMore = true
                            )
                        },
                        onFailure = {
                            publish(InvoiceListEvent.Error(it.message.orEmpty()))
                        },
                        onSuccess = {},
                    )
            }
        }
    }

    private suspend fun getInvoices(): List<InvoiceListItem> = invoiceRepository.getInvoices(
        page = page.toLong(),
        limit = LIMIT,
        minIssueDate = _state.value.filter.minIssueDate?.toString(),
        maxIssueDate = _state.value.filter.maxIssueDate?.toString(),
        minDueDate = _state.value.filter.minDueDate?.toString(),
        maxDueDate = _state.value.filter.maxDueDate?.toString(),
        senderCompany = _state.value.filter.senderCompany,
        recipientCompany = _state.value.filter.recipientCompany
    )


    companion object {
        private const val LIMIT = 20
    }
}