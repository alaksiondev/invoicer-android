package features.invoice.presentation.screens.invoicelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.buttons.CloseButton
import features.auth.design.system.components.feedback.Feedback
import features.invoice.presentation.R
import features.invoice.presentation.screens.invoicelist.state.InvoiceListCallbacks
import features.invoice.presentation.screens.invoicelist.state.InvoiceListScreenModel
import features.invoice.presentation.screens.invoicelist.state.InvoiceListState
import features.invoice.presentation.screens.invoicelist.state.rememberInvoiceListCallbacks
import foundation.design.system.tokens.Spacing

internal class InvoiceListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<InvoiceListScreenModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val callbacks = rememberInvoiceListCallbacks(
            onClose = { navigator?.pop() },
            onRetry = {}
        )

        LaunchedEffect(Unit) {
            viewModel.loadPage()
        }

        StateContent(
            callbacks = callbacks,
            state = state
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: InvoiceListState,
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
            when {
                state.showError -> Feedback(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Spacing.medium),
                    primaryActionText = stringResource(R.string.invoice_list_error_retry),
                    onPrimaryAction = callbacks.onRetry,
                    icon = Icons.Outlined.ErrorOutline,
                    title = stringResource(R.string.invoice_list_error_title),
                    description = stringResource(R.string.invoice_list_error_description)
                )

                state.isLoading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(it),
                        contentPadding = PaddingValues(Spacing.medium)
                    ) {

                    }
                }
            }
        }
    }
}