package io.github.alaksion.features.home.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import io.github.alaksion.features.home.presentation.container.HomeContainerScreen
import io.github.alaksion.invoicer.foundation.navigation.InvoicerScreen

val homeContainerScreens = screenModule {
    register<InvoicerScreen.Home> { HomeContainerScreen() }
}
