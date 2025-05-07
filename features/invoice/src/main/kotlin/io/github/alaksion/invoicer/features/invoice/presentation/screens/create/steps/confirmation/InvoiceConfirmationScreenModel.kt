package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.confirmation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.network.request.handle
import foundation.network.request.launchRequest
import foundation.watchers.NewInvoicePublisher
import io.github.alaksion.invoicer.features.invoice.domain.model.CreateInvoiceActivityModel
import io.github.alaksion.invoicer.features.invoice.domain.model.CreateInvoiceModel
import io.github.alaksion.invoicer.features.invoice.domain.repository.InvoiceRepository
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import io.github.alaksion.invoicer.foundation.utils.date.defaultFormat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class InvoiceConfirmationScreenModel(
    private val manager: CreateInvoiceManager,
    private val repository: InvoiceRepository,
    private val dispatcher: CoroutineDispatcher,
    private val newInvoicePublisher: NewInvoicePublisher
) : ScreenModel {

    private val _state = MutableStateFlow(InvoiceConfirmationState())
    val state: StateFlow<InvoiceConfirmationState> = _state

    private val _events = MutableSharedFlow<InvoiceConfirmationEvent>()
    val events = _events.asSharedFlow()

    fun initState() {
        _state.update {
            InvoiceConfirmationState(
                senderCompanyName = manager.senderCompanyName,
                senderCompanyAddress = manager.senderCompanyAddress,
                recipientCompanyName = manager.recipientCompanyName,
                recipientCompanyAddress = manager.recipientCompanyAddress,
                issueDate = manager.issueDate.defaultFormat(),
                dueDate = manager.dueDate.defaultFormat(),
                intermediaryName = manager.intermediaryName,
                beneficiaryName = manager.beneficiaryName,
                activities = manager.activities,
                externalId = manager.externalId
            )
        }
    }

    fun submitInvoice() {
        screenModelScope.launch(dispatcher) {
            launchRequest {
                repository.createInvoice(
                    payload = CreateInvoiceModel(
                        externalId = manager.externalId,
                        senderCompanyName = manager.senderCompanyName,
                        senderCompanyAddress = manager.senderCompanyAddress,
                        recipientCompanyName = manager.recipientCompanyName,
                        recipientCompanyAddress = manager.recipientCompanyAddress,
                        issueDate = manager.issueDate,
                        dueDate = manager.dueDate,
                        beneficiaryId = manager.beneficiaryId,
                        intermediaryId = manager.intermediaryId,
                        activities = manager.activities.map {
                            CreateInvoiceActivityModel(
                                description = it.description,
                                unitPrice = it.unitPrice,
                                quantity = it.quantity,
                            )
                        }
                    )
                )
            }.handle(
                onStart = {
                    _state.update { it.copy(isLoading = true) }
                },
                onFinish = {
                    _state.update { it.copy(isLoading = false) }
                },
                onSuccess = {
                    manager.clear()
                    newInvoicePublisher.publish(Unit)
                    _events.emit(InvoiceConfirmationEvent.Success)
                },
                onFailure = {
                    _events.emit((InvoiceConfirmationEvent.Error(it.message.orEmpty())))
                }
            )
        }
    }

}