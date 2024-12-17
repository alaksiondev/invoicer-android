package features.invoice.presentation.screens.invoicelist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.buttons.CloseButton
import features.invoice.presentation.R

internal class InvoiceListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<InvoiceListScreenModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val callbacks = rememberInvoiceListCallbacks(
            onClose = { navigator?.pop() }
        )

        LaunchedEffect(Unit) {
            viewModel.loadPage()
        }

        StateContent(
            callbacks = callbacks
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        callbacks: InvoiceListCallbacks
    ) {
        Scaffold(
            topBar = {
                MediumTopAppBar(
                    title = { Text(stringResource(R.string.invoice_list_title)) },
                    navigationIcon = { CloseButton(onBackClick = callbacks.onClose) },
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {

            }
        }
    }
}