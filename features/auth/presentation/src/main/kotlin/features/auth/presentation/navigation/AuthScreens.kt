package features.auth.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.auth.presentation.screens.menu.AuthMenuScreen
import foundation.navigation.InvoicerScreen

val authScreens = screenModule {
    register<InvoicerScreen.Auth.AuthMenu> {
        AuthMenuScreen()
    }
}