package features.invoice.presentation.screens.invoicelist.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import features.invoice.domain.model.InvoiceListItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate

internal data class InvoiceListState(
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val isLoadingMore: Boolean = false,
    val invoices: ImmutableList<InvoiceListItem> = persistentListOf(),
    val filter: InvoiceListFilterState = InvoiceListFilterState(),
)

internal data class InvoiceListFilterState(
    val minIssueDate: LocalDate? = null,
    val maxIssueDate: LocalDate? = null,
    val minDueDate: LocalDate? = null,
    val maxDueDate: LocalDate? = null,
    val senderCompany: String? = null,
    val recipientCompany: String? = null,
)

internal data class InvoiceListCallbacks(
    val onClose: () -> Unit,
    val onRetry: () -> Unit
)

internal sealed interface InvoiceListEvent {
    data class Error(val message: String) : InvoiceListEvent
}

@Composable
internal fun rememberInvoiceListCallbacks(
    onClose: () -> Unit,
    onRetry: () -> Unit
): InvoiceListCallbacks {
    return remember {
        InvoiceListCallbacks(
            onClose = onClose,
            onRetry = onRetry
        )
    }
}