package features.auth.presentation.screens.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.auth.presentation.screens.signin.SignInScreen
import foundation.navigation.InvoicerScreen

val authScreens = screenModule {
    register<InvoicerScreen.Auth.SignIn> {
        SignInScreen()
    }
}