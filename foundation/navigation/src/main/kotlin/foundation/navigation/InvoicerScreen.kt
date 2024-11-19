package foundation.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed interface InvoicerScreen : ScreenProvider {
    sealed interface Auth : InvoicerScreen {
        data object AuthMenu : Auth
        data object SignUp : Auth
        data object Startup : Auth
    }
}