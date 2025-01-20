package features.invoice.presentation.screens.create.steps.activities

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
import foundation.events.EventAware
import foundation.events.EventPublisher
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class InvoiceActivitiesScreenModel(
    private val dispatcher: CoroutineDispatcher,
) : ScreenModel,
    EventAware<InvoiceActivitiesEvent> by EventPublisher() {

    private val _state = MutableStateFlow(InvoiceActivitiesState())
    val state: StateFlow<InvoiceActivitiesState> = _state

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
        val unitPrice = _state.value.formState.unitPrice.toLongOrNull() ?: 0
        val quantity = _state.value.formState.quantity.toIntOrNull() ?: 0

        screenModelScope.launch(dispatcher) {
            if (unitPrice <= 0) {
                publish(InvoiceActivitiesEvent.ActivityUnitPriceError)
                return@launch
            }

            if (quantity <= 0) {
                publish(InvoiceActivitiesEvent.ActivityQuantityError)
                return@launch
            }

            _state.update { oldState ->
                val newActivity = CreateInvoiceActivityUiModel(
                    description = oldState.formState.description,
                    unitPrice = unitPrice,
                    quantity = quantity
                )

                oldState.copy(
                    activities = (oldState.activities + newActivity).toPersistentList(),
                    formState = AddActivityFormState()
                )
            }

            clearForm()
        }
    }
}