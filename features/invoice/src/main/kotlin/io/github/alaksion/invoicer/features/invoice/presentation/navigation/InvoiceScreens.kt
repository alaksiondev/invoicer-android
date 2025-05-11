package io.github.alaksion.invoicer.features.invoice.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId.InvoiceExternalIdStep
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.InvoiceListScreen

val invoiceScreens = screenModule {
    register<InvoicerScreen.Invoices.List> {
        InvoiceListScreen()
    }
    register<InvoicerScreen.Invoices.Create> {
        InvoiceExternalIdStep()
    }
}
