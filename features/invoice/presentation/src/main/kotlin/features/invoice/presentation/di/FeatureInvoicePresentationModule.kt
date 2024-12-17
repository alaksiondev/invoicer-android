package features.invoice.presentation.di

import features.invoice.presentation.screens.invoicelist.state.InvoiceListScreenModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val featureInvoicePresentationModule = module {
    factory<InvoiceListScreenModel> {
        InvoiceListScreenModel(
            invoiceRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }
}