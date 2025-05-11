package io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.network.request.handle
import foundation.network.request.launchRequest
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceList
import io.github.alaksion.invoicer.features.invoice.domain.repository.InvoiceRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class InvoiceListScreenModel(
    private val invoiceRepository: InvoiceRepository,
    private val dispatcher: CoroutineDispatcher,
) : ScreenModel {

    private var page = 0
    private var isFirstPageLoaded = false

    private val _state = MutableStateFlow(InvoiceListState())
    val state: StateFlow<InvoiceListState> get() = _state

    private val _events = MutableSharedFlow<InvoiceListEvent>()
    val events = _events.asSharedFlow()

    fun loadPage(force: Boolean = false) {
        if (isFirstPageLoaded.not() || force) {
            screenModelScope.launch(dispatcher) {
                launchRequest { getInvoices() }
                    .handle(
                        onStart = {
                            _state.value = _state.value.copy(
                                mode = InvoiceListMode.Loading,
                            )
                        },
                        onSuccess = { response ->
                            _state.value = _state.value.copy(
                                invoices = (response.items).toPersistentList(),
                                mode = InvoiceListMode.Content
                            )
                            isFirstPageLoaded = true
                        },
                        onFinish = { /* no op */ },
                        onFailure = {
                            _state.value = _state.value.copy(
                                mode = InvoiceListMode.Error
                            )
                        }
                    )
            }
        }
    }

    fun nextPage() {
        if (isFirstPageLoaded && _state.value.isLoadingMore.not()) {
            page++
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
                                isLoadingMore = false
                            )
                        },
                        onFailure = {
                            _events.emit(InvoiceListEvent.Error(it.message.orEmpty()))
                        },
                        onSuccess = {
                            _state.update { oldState ->
                                oldState.copy(
                                    invoices = (oldState.invoices + it.items).toImmutableList(),
                                )
                            }
                        },
                    )
            }
        }
    }

    private suspend fun getInvoices(): InvoiceList = invoiceRepository.getInvoices(
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
