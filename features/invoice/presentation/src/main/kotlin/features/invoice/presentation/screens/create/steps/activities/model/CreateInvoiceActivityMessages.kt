package features.invoice.presentation.screens.create.steps.activities.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import features.invoice.presentation.R

internal data class SnackMessages(
    val unitPriceError: String,
    val quantityError: String
)

@Composable
internal fun rememberSnackMessages(): SnackMessages {
    val unitPriceMsg = stringResource(R.string.invoice_add_activity_error_unit_price)
    val quantityMsg = stringResource(R.string.invoice_add_activity_error_quantity)

    return remember {
        SnackMessages(
            unitPriceError = unitPriceMsg,
            quantityError = quantityMsg
        )
    }
}