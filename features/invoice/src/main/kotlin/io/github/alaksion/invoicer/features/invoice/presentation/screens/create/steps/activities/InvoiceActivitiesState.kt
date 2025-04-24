package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities

import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
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
    val unitPriceParsed = unitPrice.toLongOrNull() ?: 0
    val quantityParsed = quantity.toIntOrNull() ?: 0

    val formButtonEnabled =
        description.isNotEmpty() &&
                unitPriceParsed > 0 &&
                (quantityParsed in 1..100)
}

sealed interface InvoiceActivitiesEvent {
    data object ActivityUnitPriceError : InvoiceActivitiesEvent
    data object ActivityQuantityError : InvoiceActivitiesEvent
}
