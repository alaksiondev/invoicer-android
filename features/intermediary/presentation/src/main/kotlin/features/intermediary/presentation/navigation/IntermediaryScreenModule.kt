package features.intermediary.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.intermediary.presentation.screen.create.CreateIntermediaryFlow
import features.intermediary.presentation.screen.intermediarylist.IntermediaryListScreen
import foundation.navigation.InvoicerScreen

val intermediaryScreens = screenModule {
    register<InvoicerScreen.Intermediary.List> {
        IntermediaryListScreen()
    }

    register<InvoicerScreen.Intermediary.Create> {
        CreateIntermediaryFlow()
    }
}