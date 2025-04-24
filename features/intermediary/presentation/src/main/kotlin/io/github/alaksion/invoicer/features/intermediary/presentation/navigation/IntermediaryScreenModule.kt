package io.github.alaksion.invoicer.features.intermediary.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryFlow
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.IntermediaryListScreen

val intermediaryScreens = screenModule {
    register<InvoicerScreen.Intermediary.List> {
        IntermediaryListScreen()
    }

    register<InvoicerScreen.Intermediary.Create> {
        CreateIntermediaryFlow()
    }
}