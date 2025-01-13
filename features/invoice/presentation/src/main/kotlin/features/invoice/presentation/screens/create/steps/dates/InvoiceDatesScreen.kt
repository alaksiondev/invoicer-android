package features.invoice.presentation.screens.create.steps.dates

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.spacer.Spacer
import features.invoice.presentation.R
import features.invoice.presentation.screens.create.components.CreateInvoiceBaseForm
import features.invoice.presentation.screens.create.steps.dates.components.DatePickerVisibility
import features.invoice.presentation.screens.create.steps.dates.components.InvoiceDatePicker
import foundation.date.impl.defaultFormat
import foundation.events.EventEffect

internal class InvoiceDatesScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
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
            onBack = { navigator?.pop() },
        )
    }

    @Composable
    fun StateContent(
        state: InvoiceDatesState,
        onSetDueDate: (Long) -> Unit,
        onSetIssueDate: (Long) -> Unit,
        onContinue: () -> Unit,
        onBack: () -> Unit
    ) {
        var datePickerVisibility by remember {
            mutableStateOf(DatePickerVisibility.None)
        }

        CreateInvoiceBaseForm(
            modifier = Modifier.fillMaxSize(),
            title = stringResource(R.string.invoice_create_dates_title),
            onBack = onBack,
            onContinue = onContinue,
            buttonEnabled = false,
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
                        text = state.parsedIssueDate.defaultFormat()
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
                        state.parsedDueDate.defaultFormat()
                    )
                },
                trailingContent = {
                    TextButton(
                        onClick = {

                        }
                    ) {
                        Text(stringResource(R.string.invoice_create_dates_change_button))
                    }
                }
            )
            Spacer(1f)
        }

        InvoiceDatePicker(
            visibility = datePickerVisibility,
            onDismissRequest = { datePickerVisibility = DatePickerVisibility.None },
            dueDate = state.dueDate,
            issueDate = state.issueDate,
            onSelectIssueDate = onSetIssueDate,
            onSelectDueDate = onSetDueDate,
        )
    }
}