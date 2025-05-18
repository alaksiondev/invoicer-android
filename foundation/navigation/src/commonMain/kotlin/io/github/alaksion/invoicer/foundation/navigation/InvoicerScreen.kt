package io.github.alaksion.invoicer.foundation.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed interface InvoicerScreen : ScreenProvider {
    sealed interface Auth : InvoicerScreen {
        data object AuthMenu : Auth
        data object Startup : Auth
    }

    data object Home : InvoicerScreen

    sealed interface Invoices : InvoicerScreen {
        data object List : Invoices
        data object Create : Invoices
    }

    sealed interface Beneficiary : InvoicerScreen {
        data object List : Beneficiary
        data object Create : Beneficiary
    }

    sealed interface Intermediary : InvoicerScreen {
        data object List : Beneficiary
        data object Create : Beneficiary
    }

    sealed interface Authorization : ScreenProvider {
        data object Home : Authorization
    }
}
