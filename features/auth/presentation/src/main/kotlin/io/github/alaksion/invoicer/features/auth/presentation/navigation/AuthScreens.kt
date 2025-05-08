package io.github.alaksion.invoicer.features.auth.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.features.auth.presentation.screens.login.LoginScreen

val authScreens = screenModule {
    register<InvoicerScreen.Auth.AuthMenu> {
        LoginScreen()
    }
}