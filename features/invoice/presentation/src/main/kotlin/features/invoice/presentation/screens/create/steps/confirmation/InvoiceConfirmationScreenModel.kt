package features.invoice.presentation.screens.create.steps.confirmation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.invoice.domain.model.CreateInvoiceActivityModel
import features.invoice.domain.model.CreateInvoiceModel
import features.invoice.domain.repository.InvoiceRepository
import features.invoice.presentation.screens.create.CreateInvoiceManager
import foundation.date.impl.defaultFormat
import foundation.date.impl.toLocalDate
import foundation.events.EventAware
import foundation.events.EventPublisher
import foundation.network.request.handle
import foundation.network.request.launchRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

internal class InvoiceConfirmationScreenModel(
    private val manager: CreateInvoiceManager,
    private val repository: InvoiceRepository,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel, EventAware<InvoiceConfirmationEvent> by EventPublisher() {

    private val _state = MutableStateFlow(InvoiceConfirmationState())
    val state: StateFlow<InvoiceConfirmationState> = _state

    fun initState() {
        _state.update {
            InvoiceConfirmationState(
                senderCompanyName = manager.senderCompanyName,
                senderCompanyAddress = manager.senderCompanyAddress,
                recipientCompanyName = manager.recipientCompanyName,
                recipientCompanyAddress = manager.recipientCompanyAddress,
                issueDate = manager.issueDate.toLocalDate(TimeZone.currentSystemDefault())
                    .defaultFormat(),
                dueDate = manager.dueDate.toLocalDate(TimeZone.currentSystemDefault())
                    .defaultFormat(),
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
                        issueDate = Instant.fromEpochMilliseconds(manager.issueDate),
                        dueDate = Instant.fromEpochMilliseconds(manager.dueDate),
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
                    publish(InvoiceConfirmationEvent.Success)
                },
                onFailure = {
                    publish(InvoiceConfirmationEvent.Error(it.message.orEmpty()))
                }
            )
        }
    }

}