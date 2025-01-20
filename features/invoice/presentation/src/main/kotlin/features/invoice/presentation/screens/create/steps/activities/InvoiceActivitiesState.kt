package features.invoice.presentation.screens.create.steps.activities

import features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class InvoiceActivitiesState(
    val activities: ImmutableList<CreateInvoiceActivityUiModel> = persistentListOf(),
    val formState: AddActivityFormState = AddActivityFormState()
) {
    val continueEnabled = activities.isNotEmpty()
}

internal data class AddActivityFormState(
    val description: String = "",
    val unitPrice: String = "",
    val quantity: String = ""
) {
    val formButtonEnabled =
        description.isNotEmpty() && unitPrice.isNotEmpty() && quantity.isNotEmpty()
}

sealed interface InvoiceActivitiesEvent {
    data object ActivityUnitPriceError : InvoiceActivitiesEvent
    data object ActivityQuantityError : InvoiceActivitiesEvent
}
