package features.home.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.home.presentation.screens.home.HomeScreen
import foundation.navigation.InvoicerScreen

val homeScreens = screenModule {
    register<InvoicerScreen.Home> { HomeScreen() }
}
