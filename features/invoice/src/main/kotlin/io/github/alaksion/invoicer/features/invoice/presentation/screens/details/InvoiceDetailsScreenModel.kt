package io.github.alaksion.invoicer.features.invoice.presentation.screens.details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.network.RequestError
import foundation.network.request.handle
import foundation.network.request.launchRequest
import io.github.alaksion.invoicer.features.invoice.domain.repository.InvoiceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val NotFound = 400
private const val Forbidden = 403

internal class InvoiceDetailsScreenModel(
    private val invoiceRepository: InvoiceRepository,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel {

    private val _state = MutableStateFlow(InvoiceDetailsState())
    val state: StateFlow<InvoiceDetailsState> = _state

    fun initState(
        invoiceId: String
    ) {
        screenModelScope.launch(dispatcher) {
            launchRequest { invoiceRepository.getInvoiceDetails(invoiceId) }
                .handle(
                    onStart = {
                        _state.update {
                            it.copy(mode = InvoiceDetailsMode.Loading)
                        }
                    },
                    onSuccess = {
                        _state.update {
                            it.copy(
                                externalId = it.externalId,
                                senderCompany = it.senderCompany,
                                recipientCompany = it.recipientCompany,
                                issueDate = it.issueDate,
                                dueDate = it.dueDate,
                                beneficiary = it.beneficiary,
                                intermediary = it.intermediary,
                                createdAt = it.createdAt,
                                updatedAt = it.updatedAt,
                                activities = it.activities,
                                mode = InvoiceDetailsMode.Content
                            )
                        }
                    },
                    onFailure = { requestError ->
                        _state.update { oldState ->
                            oldState.copy(
                                mode = InvoiceDetailsMode.Error(
                                    errorType = requestError.toErrorType()
                                )
                            )
                        }
                    }
                )
        }
    }

    private fun RequestError.toErrorType(): InvoiceDetailsErrorType {
        return when (this) {
            is RequestError.Other -> InvoiceDetailsErrorType.Generic
            is RequestError.Http -> {

                when (this.errorCode) {
                    NotFound, Forbidden -> InvoiceDetailsErrorType.NotFound
                    else -> InvoiceDetailsErrorType.Generic
                }
            }
        }
    }
}
