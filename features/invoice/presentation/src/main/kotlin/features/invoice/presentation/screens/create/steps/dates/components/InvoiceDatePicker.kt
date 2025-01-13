package features.invoice.presentation.screens.create.steps.dates.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import features.invoice.presentation.R

internal enum class DatePickerVisibility {
    DueDate,
    IssueDate,
    None
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InvoiceDatePicker(
    visibility: DatePickerVisibility,
    dueDate: Long,
    issueDate: Long,
    onDismissRequest: () -> Unit,
    onSelectIssueDate: (Long) -> Unit,
    onSelectDueDate: (Long) -> Unit,
) {
    val issueDatePicker = rememberDatePickerState(
        initialSelectedDateMillis = dueDate
    )
    val dueDatePicker = rememberDatePickerState(
        initialSelectedDateMillis = issueDate
    )

    when (visibility) {
        DatePickerVisibility.DueDate -> DatePickerDialog(
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                Button(
                    onClick = {
                        issueDatePicker.selectedDateMillis?.let {
                            onSelectIssueDate(it)
                            onDismissRequest()
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.invoice_create_dates_dialog_confirm)
                    )
                }

            }
        ) {
            DatePicker(
                state = dueDatePicker
            )
        }

        DatePickerVisibility.IssueDate -> DatePickerDialog(
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                Button(
                    onClick = {
                        issueDatePicker.selectedDateMillis?.let {
                            onSelectDueDate(it)
                            onDismissRequest()
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.invoice_create_dates_dialog_confirm)
                    )
                }
            }
        ) {
            DatePicker(
                state = issueDatePicker
            )
        }

        DatePickerVisibility.None -> {}
    }

}