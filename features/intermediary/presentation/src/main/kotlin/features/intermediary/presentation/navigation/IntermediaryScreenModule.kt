package features.intermediary.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.screenModule
import cafe.adriel.voyager.core.screen.Screen
import features.intermediary.presentation.screen.intermediarylist.IntermediaryListScreen
import foundation.navigation.InvoicerScreen

val intermediaryScreens = screenModule {
    register<InvoicerScreen.Intermediary.List> {
        IntermediaryListScreen()
    }

    register<InvoicerScreen.Intermediary.Create> {
        object : Screen {
            @Composable
            override fun Content() {

            }
        }
    }
}