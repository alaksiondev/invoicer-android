package io.github.alaksion.invoicer.features.auth.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import io.github.alaksion.invoicer.foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreen
import io.github.alaksion.invoicer.features.auth.presentation.screens.startup.StartupScreen

val authScreens = screenModule {
    register<InvoicerScreen.Auth.AuthMenu> {
        LoginScreen()
    }

    register<InvoicerScreen.Auth.Startup> {
        StartupScreen()
    }
}
