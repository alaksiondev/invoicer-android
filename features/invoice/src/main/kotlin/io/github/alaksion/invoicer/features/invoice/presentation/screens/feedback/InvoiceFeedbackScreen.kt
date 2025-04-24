package io.github.alaksion.invoicer.features.invoice.presentation.screens.feedback

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.InvoiceListScreen
import foundation.designsystem.components.feedback.Feedback
import foundation.designsystem.tokens.Spacing
import io.github.alasion.invoicer.features.invoice.R

internal class InvoiceFeedbackScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        StateContent(
            onClearFlow = {
                navigator?.popUntil { it.key == InvoiceListScreen.SCREEN_KEY }
            }
        )
    }

    @Composable
    fun StateContent(
        onClearFlow: () -> Unit
    ) {
        BackHandler {
            // no-op: Disable back button
        }

        Scaffold {
            Feedback(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Spacing.medium)
                    .padding(it),
                primaryActionText = stringResource(R.string.invoice_feedback_cta),
                title = stringResource(R.string.invoice_feedback_title),
                description = stringResource(R.string.invoice_feedback_description),
                onPrimaryAction = {
                    onClearFlow()
                },
                icon = Icons.Outlined.Check
            )
        }
    }

}
