package features.auth.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.auth.presentation.screens.login.LoginScreen
import foundation.navigation.InvoicerScreen

val authScreens = screenModule {
    register<InvoicerScreen.Auth.AuthMenu> {
        LoginScreen()
    }
}