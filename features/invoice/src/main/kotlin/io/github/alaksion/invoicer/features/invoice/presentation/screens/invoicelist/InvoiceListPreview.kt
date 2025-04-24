package io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import foundation.designsystem.components.preview.ThemeContainer
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListCallbacks
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListMode
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListState

private class InvoiceListStateProvider : PreviewParameterProvider<InvoiceListState> {
    override val values: Sequence<InvoiceListState>
        get() = sequenceOf(
            InvoiceListState(
                mode = InvoiceListMode.Loading
            ),
            InvoiceListState(
                mode = InvoiceListMode.Error
            )
        )
}

@Composable
@Preview
private fun InvoiceListPreview(
    @PreviewParameter(InvoiceListStateProvider::class) state: InvoiceListState
) {
    ThemeContainer {
        InvoiceListScreen()
            .StateContent(
                state = state,
                callbacks = InvoiceListCallbacks
            )
    }
}