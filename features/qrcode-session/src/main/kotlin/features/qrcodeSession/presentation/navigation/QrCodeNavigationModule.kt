package features.qrcodeSession.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.qrcodeSession.presentation.screens.AuthorizationHomeScreen
import foundation.navigation.InvoicerScreen

val qrCodeNavigationModule = screenModule {
    register<InvoicerScreen.Authorization.Home> {
        AuthorizationHomeScreen()
    }
}