package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private val VALID_ACTIVITY_QUANTITY_RANGE = 1..100

internal class InvoiceActivitiesScreenModel(
    private val dispatcher: CoroutineDispatcher,
    private val createInvoiceManager: CreateInvoiceManager
) : ScreenModel {

    private val _state = MutableStateFlow(InvoiceActivitiesState())
    val state: StateFlow<InvoiceActivitiesState> = _state

    private val _events = MutableSharedFlow<InvoiceActivitiesEvent>()
    val events = _events.asSharedFlow()

    fun initState() {
        _state.update {
            it.copy(
                activities = createInvoiceManager.activities.toPersistentList()
            )
        }
    }

    fun updateFormDescription(description: String) {
        _state.update { oldState ->
            oldState.copy(
                formState = oldState.formState.copy(
                    description = description
                )
            )
        }
    }

    fun updateFormQuantity(quantity: String) {
        _state.update { oldState ->
            oldState.copy(
                formState = oldState.formState.copy(
                    quantity = quantity
                )
            )
        }
    }

    fun updateFormUnitPrice(unitPrice: String) {
        _state.update { oldState ->
            oldState.copy(
                formState = oldState.formState.copy(
                    unitPrice = unitPrice
                )
            )
        }
    }

    fun clearForm() {
        _state.update { oldState ->
            oldState.copy(
                formState = AddActivityFormState()
            )
        }
    }

    fun addActivity() {
        screenModelScope.launch(dispatcher) {
            val unitPrice = _state.value.formState.unitPriceParsed
            val quantity = _state.value.formState.quantityParsed

            if (unitPrice <= 0) {
                _events.emit(InvoiceActivitiesEvent.ActivityUnitPriceError)
                return@launch
            }

            if (quantity !in VALID_ACTIVITY_QUANTITY_RANGE) {
                _events.emit(InvoiceActivitiesEvent.ActivityQuantityError)
                return@launch
            }


            val newActivity = CreateInvoiceActivityUiModel(
                description = _state.value.formState.description,
                unitPrice = unitPrice,
                quantity = quantity
            )

            _state.update { oldState ->
                oldState.copy(
                    activities = (oldState.activities + newActivity).toPersistentList(),
                    formState = AddActivityFormState()
                )
            }


            createInvoiceManager.activities.add(newActivity)

            clearForm()
        }
    }

    fun removeActivity(id: String) {
        _state.update { oldState ->
            oldState.copy(
                activities = oldState.activities.filter { it.id != id }.toPersistentList()
            )
        }

        val index = createInvoiceManager.activities.indexOfFirst { it.id == id }
        createInvoiceManager.activities.removeAt(index)
    }
}
