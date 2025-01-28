package features.invoice.presentation.screens.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import features.invoice.presentation.screens.create.steps.externalId.InvoiceExternalIdScreen
import org.koin.mp.KoinPlatform.getKoin

internal class CreateInvoiceFlow : Screen {

    @Composable
    override fun Content() {
        DisposableEffect(Unit) {
            onDispose {
                val manager = getKoin().get<CreateInvoiceManager>()
                manager.clear()
            }
        }

        Navigator(
            screen = InvoiceExternalIdScreen()
        ) {
            SlideTransition(it)
        }
    }
}