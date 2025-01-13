package features.invoice.presentation.screens.create.steps.dates

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.preview.PreviewContainer
import features.auth.design.system.components.spacer.Spacer
import features.invoice.presentation.R
import features.invoice.presentation.screens.create.components.CreateInvoiceBaseForm
import foundation.date.impl.defaultFormat
import foundation.events.EventEffect
import kotlinx.datetime.LocalDate

internal class InvoiceDatesScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = koinScreenModel<InvoiceDatesScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        EventEffect(screenModel) {
            when (it) {
                InvoiceDateEvents.Continue -> {}
            }
        }

        LaunchedEffect(Unit) { screenModel.initState() }

        StateContent(
            state = state,
            onSetIssueDate = screenModel::updateIssueDate,
            onSetDueDate = screenModel::updateDueDate,
            onContinue = screenModel::submit,
            onBack = { navigator?.pop() }
        )
    }

    @Composable
    fun StateContent(
        state: InvoiceDatesState,
        onSetDueDate: (LocalDate) -> Unit,
        onSetIssueDate: (LocalDate) -> Unit,
        onContinue: () -> Unit,
        onBack: () -> Unit
    ) {
        CreateInvoiceBaseForm(
            modifier = Modifier.fillMaxSize(),
            title = stringResource(R.string.invoice_create_dates_title),
            onBack = onBack,
            onContinue = onContinue,
            buttonEnabled = state.formValid,
            buttonText = stringResource(R.string.invoice_create_continue_cta)
        ) {
            Spacer(1f)
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.invoice_create_dates_issue_date_label)
                    )
                },
                supportingContent = {
                    Text(
                        state.issueDate.defaultFormat()
                    )
                },
                trailingContent = {
                    TextButton(
                        onClick = {}
                    ) {
                        Text(stringResource(R.string.invoice_create_dates_change_button))
                    }
                }
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.invoice_create_dates_due_date_label)
                    )
                },
                supportingContent = {
                    Text(
                        state.dueDate.defaultFormat()
                    )
                },
                trailingContent = {
                    TextButton(
                        onClick = {}
                    ) {
                        Text(stringResource(R.string.invoice_create_dates_change_button))
                    }
                }
            )
            Spacer(1f)
        }
    }
}

@Composable
@Preview
private fun Preview() {
    PreviewContainer {
        InvoiceDatesScreen().StateContent(
            state = InvoiceDatesState(),
            onBack = {},
            onContinue = {},
            onSetIssueDate = {},
            onSetDueDate = {}
        )
    }
}