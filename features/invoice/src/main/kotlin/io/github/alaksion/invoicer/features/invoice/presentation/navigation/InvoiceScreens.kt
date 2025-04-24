package io.github.alaksion.invoicer.features.invoice.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId.InvoiceExternalIdScreen
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.InvoiceListScreen
import foundation.navigation.InvoicerScreen

val invoiceScreens = screenModule {
    register<InvoicerScreen.Invoices.List> {
        InvoiceListScreen()
    }
    register<InvoicerScreen.Invoices.Create> {
        InvoiceExternalIdScreen()
    }
}