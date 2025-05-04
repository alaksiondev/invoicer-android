package io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import foundation.designsystem.components.buttons.CloseButton
import foundation.designsystem.components.emptystate.EmptyState
import foundation.designsystem.components.feedback.Feedback
import foundation.designsystem.tokens.Spacing
import foundation.navigation.InvoicerScreen
import foundation.ui.LazyListPaginationEffect
import foundation.ui.events.EventEffect
import foundation.watchers.NewInvoicePublisher
import io.github.alaksion.invoicer.features.invoice.presentation.screens.details.InvoiceDetailsScreen
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.components.InvoiceListItem
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListCallbacks
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListMode
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListScreenModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListState
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.rememberInvoiceListCallbacks
import io.github.alasion.invoicer.features.invoice.R
import org.koin.java.KoinJavaComponent.getKoin

internal class InvoiceListScreen : Screen {

    companion object {
        val SCREEN_KEY = "invoice-list-screen"
    }

    override val key: ScreenKey = SCREEN_KEY

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<InvoiceListScreenModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val newInvoicePublisher = remember { getKoin().get<NewInvoicePublisher>() }

        val callbacks = rememberInvoiceListCallbacks(
            onClose = { navigator?.pop() },
            onRetry = { viewModel.loadPage() },
            onClickInvoice = {
                navigator?.push(InvoiceDetailsScreen(it))
            },
            onClickCreateInvoice = {
                navigator?.push(ScreenRegistry.get(InvoicerScreen.Invoices.Create))
            },
            onNextPage = { viewModel.nextPage() }
        )

        EventEffect(newInvoicePublisher) {
            viewModel.loadPage(force = true)
        }

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
                    navigationIcon = { CloseButton(onBackClick = callbacks::onClose) },
                )
            },
            floatingActionButton = {
                if (state.mode == InvoiceListMode.Content) {
                    FloatingActionButton(
                        onClick = callbacks::onCreateInvoiceClick
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = null
                        )
                    }
                }
            }
        ) { scaffoldPadding ->
            when (state.mode) {
                InvoiceListMode.Content -> {
                    if (state.invoices.isEmpty()) {
                        EmptyState(
                            title = stringResource(R.string.invoice_list_empty_title),
                            description = stringResource(R.string.invoice_list_empty_description),
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        val listState = rememberLazyListState()

                        LazyListPaginationEffect(
                            state = listState,
                            enabled = true
                        ) {
                            callbacks.onNextPage()
                        }

                        LazyColumn(
                            modifier = Modifier.padding(scaffoldPadding),
                            contentPadding = PaddingValues(Spacing.medium),
                            verticalArrangement = Arrangement.spacedBy(Spacing.medium),
                            state = listState
                        ) {
                            items(
                                items = state.invoices,
                                key = { item -> item.id }
                            ) { invoiceItem ->
                                InvoiceListItem(
                                    item = invoiceItem,
                                    onClick = { callbacks.onClickInvoice(invoiceItem.id) }
                                )
                            }
                        }
                    }
                }

                InvoiceListMode.Loading -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                InvoiceListMode.Error -> Feedback(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding)
                        .padding(Spacing.medium),
                    primaryActionText = stringResource(R.string.invoice_list_error_retry),
                    onPrimaryAction = callbacks::onRetry,
                    icon = Icons.Outlined.ErrorOutline,
                    title = stringResource(R.string.invoice_list_error_title),
                    description = stringResource(R.string.invoice_list_error_description)
                )
            }
        }
    }
}