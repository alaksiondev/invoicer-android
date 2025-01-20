package features.invoice.presentation.screens.create.steps.activities.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal data class CreateInvoiceActivityUiModel(
    val description: String,
    val unitPrice: Long,
    val quantity: Int
) {
    @OptIn(ExperimentalUuidApi::class)
    val id = Uuid.random().toString()
}
