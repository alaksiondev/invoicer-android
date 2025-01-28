package features.invoice.presentation.screens.feedback

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
import features.auth.design.system.components.feedback.Feedback
import features.invoice.presentation.R
import foundation.design.system.tokens.Spacing

internal class InvoiceFeedbackScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current?.parent
        StateContent(onClearFlow = { navigator?.pop() })
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
