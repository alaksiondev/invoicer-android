package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.github.alasion.invoicer.features.invoice.R

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
    now: Long,
    onDismissRequest: () -> Unit,
    onSelectIssueDate: (Long) -> Unit,
    onSelectDueDate: (Long) -> Unit,
) {
    when (visibility) {
        DatePickerVisibility.DueDate -> {
            val dueDatePicker = rememberDatePickerState(
                initialSelectedDateMillis = dueDate,
                initialDisplayedMonthMillis = now
            )

            DatePickerDialog(
                onDismissRequest = {
                    onDismissRequest()
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // BUG -> Convert selected date milis from UTC to LocalTimezone
                            dueDatePicker.selectedDateMillis?.let {
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
                    state = dueDatePicker
                )
            }
        }

        DatePickerVisibility.IssueDate -> {
            val issueDatePicker = rememberDatePickerState(
                initialSelectedDateMillis = issueDate,
                initialDisplayedMonthMillis = now
            )

            DatePickerDialog(
                onDismissRequest = {
                    onDismissRequest()
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // BUG -> Convert selected date milis from UTC to LocalTimezone
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
                    state = issueDatePicker
                )
            }
        }

        DatePickerVisibility.None -> {}
    }
}