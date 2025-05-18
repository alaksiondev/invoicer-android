package io.github.alaksion.invoicer.features.home.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import io.github.alaksion.invoicer.foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.features.home.presentation.container.HomeContainerScreen

val homeContainerScreens = screenModule {
    register<InvoicerScreen.Home> { HomeContainerScreen() }
}
