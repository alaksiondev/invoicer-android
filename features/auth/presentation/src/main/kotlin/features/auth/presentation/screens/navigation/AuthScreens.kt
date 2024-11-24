package features.auth.presentation.screens.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.auth.presentation.screens.menu.AuthMenuScreen
import features.auth.presentation.screens.signup.SignUpScreen
import foundation.navigation.InvoicerScreen

val authScreens = screenModule {
    register<InvoicerScreen.Auth.AuthMenu> {
        AuthMenuScreen()
    }

    register<InvoicerScreen.Auth.SignUp> {
        SignUpScreen()
    }
}