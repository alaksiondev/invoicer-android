package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class InvoiceExternalIdScreenModel(
    private val manager: CreateInvoiceManager,
    private val dispatcher: CoroutineDispatcher
) : ScreenModel {

    private val _state = MutableStateFlow(InvoiceExternalIdState())
    val state: StateFlow<InvoiceExternalIdState> = _state

    private val _events = MutableSharedFlow<Unit>()
    val events = _events.asSharedFlow()

    fun initState() {
        _state.update {
            it.copy(
                externalId = manager.externalId
            )
        }
    }

    fun updateExternalId(value: String) {
        _state.update {
            it.copy(
                externalId = value
            )
        }
    }

    fun submit() {
        if (_state.value.isButtonEnabled) screenModelScope.launch(dispatcher) {
            manager.externalId = _state.value.externalId
            _events.emit(Unit)
        }
    }
}
