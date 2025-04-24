package io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceListItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate

internal data class InvoiceListState(
    val mode: InvoiceListMode = InvoiceListMode.Content,
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

internal enum class InvoiceListMode {
    Loading,
    Content,
    Error,
}

internal interface InvoiceListCallbacks {
    fun onClose()
    fun onRetry()
    fun onClickInvoice(value: String)
    fun onCreateInvoiceClick()
    fun onNextPage()

    companion object : InvoiceListCallbacks {
        override fun onClose() = Unit

        override fun onRetry() = Unit

        override fun onClickInvoice(value: String) = Unit

        override fun onCreateInvoiceClick() = Unit

        override fun onNextPage() = Unit
    }
}

internal sealed interface InvoiceListEvent {
    data class Error(val message: String) : InvoiceListEvent
}

@Composable
internal fun rememberInvoiceListCallbacks(
    onClose: () -> Unit,
    onRetry: () -> Unit,
    onClickInvoice: (String) -> Unit,
    onClickCreateInvoice: () -> Unit,
    onNextPage: () -> Unit,
): InvoiceListCallbacks {
    return remember {
        object : InvoiceListCallbacks {
            override fun onClose() = onClose()

            override fun onRetry() = onRetry()

            override fun onClickInvoice(value: String) = onClickInvoice(value)

            override fun onCreateInvoiceClick() = onClickCreateInvoice()

            override fun onNextPage() = onNextPage()
        }
    }
}