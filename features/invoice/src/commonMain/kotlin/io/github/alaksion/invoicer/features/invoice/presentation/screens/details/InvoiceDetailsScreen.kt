package io.github.alaksion.invoicer.features.invoice.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

internal data class InvoiceDetailsScreen(
    private val id: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<InvoiceDetailsScreenModel>()

        LaunchedEffect(Unit) { screenModel.initState(invoiceId = id) }
    }
}
