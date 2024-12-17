package features.invoice.presentation.screens.invoicelist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import features.auth.design.system.components.preview.PreviewContainer
import features.invoice.presentation.screens.invoicelist.state.InvoiceListCallbacks
import features.invoice.presentation.screens.invoicelist.state.InvoiceListState

private class InvoiceListStateProvider : PreviewParameterProvider<InvoiceListState> {
    override val values: Sequence<InvoiceListState>
        get() = sequenceOf(
            InvoiceListState(
                isLoading = true
            ),
            InvoiceListState(
                showError = true
            )
        )
}

@Composable
@Preview
private fun InvoiceListPreview(
    @PreviewParameter(InvoiceListStateProvider::class) state: InvoiceListState
) {
    PreviewContainer {
        InvoiceListScreen()
            .StateContent(
                state = state,
                callbacks = InvoiceListCallbacks(
                    onClose = {},
                    onRetry = {}
                )
            )
    }
}