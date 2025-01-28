package features.invoice.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.invoice.presentation.screens.create.steps.externalId.InvoiceExternalIdScreen
import features.invoice.presentation.screens.invoicelist.InvoiceListScreen
import foundation.navigation.InvoicerScreen

val invoiceScreens = screenModule {
    register<InvoicerScreen.Invoices.List> {
        InvoiceListScreen()
    }
    register<InvoicerScreen.Invoices.Create> {
        InvoiceExternalIdScreen()
    }
}