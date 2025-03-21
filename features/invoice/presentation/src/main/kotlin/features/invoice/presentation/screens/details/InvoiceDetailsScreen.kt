package features.invoice.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

internal data class InvoiceDetailsScreen(
    private val id: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<InvoiceDetailsScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) { screenModel.initState(invoiceId = id) }

        StateContent(
            state = state
        )
    }

    @Composable
    fun StateContent(
        state: InvoiceDetailsState
    ) {

    }

}
