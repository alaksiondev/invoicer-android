package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import invoicer.features.invoice.generated.resources.Res
import invoicer.features.invoice.generated.resources.invoice_add_activity_error_quantity
import invoicer.features.invoice.generated.resources.invoice_add_activity_error_unit_price
import org.jetbrains.compose.resources.stringResource

internal data class InvoiceActivityMessages(
    val unitPriceError: String,
    val quantityError: String
)

@Composable
internal fun rememberSnackMessages(): InvoiceActivityMessages {
    val unitPriceMsg = stringResource(Res.string.invoice_add_activity_error_unit_price)
    val quantityMsg = stringResource(Res.string.invoice_add_activity_error_quantity)

    return remember {
        InvoiceActivityMessages(
            unitPriceError = unitPriceMsg,
            quantityError = quantityMsg
        )
    }
}
