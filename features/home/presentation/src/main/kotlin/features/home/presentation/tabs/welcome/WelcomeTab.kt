package features.home.presentation.tabs.welcome

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import features.home.presentation.tabs.welcome.components.WelcomeActions
import foundation.design.system.tokens.Spacing
import foundation.navigation.InvoicerScreen

internal object WelcomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "",
            icon = null
        )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current?.parent

        val callbacks = rememberWelcomeCallbacks(
            onInvoiceClick = {
                navigator?.push(
                    ScreenRegistry.get(InvoicerScreen.Invoices)
                )
            },
            onBeneficiaryClick = {
                navigator?.push(ScreenRegistry.get(InvoicerScreen.Beneficiary.List))
            }
        )
        StateContent(
            callbacks = callbacks
        )
    }

    @Composable
    fun StateContent(
        callbacks: WelcomeCallbacks
    ) {
        Scaffold {
            LazyColumn(
                modifier = Modifier.padding(it),
                contentPadding = PaddingValues(Spacing.medium)
            ) {
                item {
                    WelcomeActions(
                        modifier = Modifier.fillParentMaxWidth(),
                        onBeneficiaryClick = {},
                        onInvoiceClick = callbacks.onInvoiceClick,
                        onIntermediaryClick = {}
                    )
                }
            }
        }
    }

}