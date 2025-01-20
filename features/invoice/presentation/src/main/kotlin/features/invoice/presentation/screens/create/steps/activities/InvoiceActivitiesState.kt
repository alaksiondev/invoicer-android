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
)

enum class InvoiceActivitiesEvent {
    ActivityUnitPriceError,
    ActivityQuantityError
}
