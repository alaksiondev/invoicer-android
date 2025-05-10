package io.github.alaksion.invoicer.features.home.presentation.tabs.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.theme.InvoicerTheme
import foundation.designsystem.tokens.Spacing
import foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.features.home.presentation.tabs.welcome.components.WelcomeActions

internal object HomeTab : Tab {

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
                    ScreenRegistry.get(InvoicerScreen.Invoices.List)
                )
            },
            onBeneficiaryClick = {
                navigator?.push(ScreenRegistry.get(InvoicerScreen.Beneficiary.List))
            },
            onIntermediaryClick = {
                navigator?.push(ScreenRegistry.get(InvoicerScreen.Intermediary.List))
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(Spacing.medium)
            ) {
                Text(
                    text = "Hello, John Dona",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                VerticalSpacer(SpacerSize.Small)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null
                        )
                    }
                }
                VerticalSpacer(SpacerSize.XLarge3)
                WelcomeActions(
                    modifier = Modifier.fillMaxWidth(),
                    onBeneficiaryClick = callbacks.onBeneficiaryClick,
                    onInvoiceClick = callbacks.onInvoiceClick,
                    onIntermediaryClick = callbacks.onIntermediaryClick
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    InvoicerTheme {
        HomeTab.StateContent(
            rememberWelcomeCallbacks(
                onInvoiceClick = {},
                onBeneficiaryClick = {}
            ) { })
    }
}