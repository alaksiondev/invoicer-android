package features.home.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.home.presentation.container.HomeContainerScreen
import foundation.navigation.InvoicerScreen

val homeContainerScreens = screenModule {
    register<InvoicerScreen.Home> { HomeContainerScreen() }
}
