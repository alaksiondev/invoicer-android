package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import invoicer.features.invoice.generated.resources.Res
import invoicer.features.invoice.generated.resources.invoice_create_dates_dialog_confirm
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import io.github.alaksion.invoicer.foundation.utils.date.toZeroHour
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InvoiceDatePicker(
    isVisible: Boolean,
    title: String,
    selectedDate: Instant,
    minimumDate: Instant,
    onSelectDate: (Instant) -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (isVisible) {
        val pickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.toEpochMilliseconds(),
            selectableDates = MinSelectableDate(
                minYear = minimumDate.toLocalDateTime(TimeZone.currentSystemDefault()).year,
                minEpoch = minimumDate.toZeroHour(TimeZone.UTC).toEpochMilliseconds()
            )
        )
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                PrimaryButton(
                    modifier = Modifier.padding(Spacing.medium),
                    label = stringResource(Res.string.invoice_create_dates_dialog_confirm),
                    onClick = {
                        pickerState.selectedDateMillis?.let { selectedDate ->
                            val selectedInstant = Instant.fromEpochMilliseconds(selectedDate)
                            onSelectDate(
                                selectedInstant
                                    .toLocalDateTime(TimeZone.UTC)
                                    .toInstant(TimeZone.currentSystemDefault())
                            )
                        }
                        onDismissRequest()
                    }
                )
            }
        ) {
            DatePicker(
                state = pickerState,
                title = {
                    Text(
                        text = title,
                        modifier = Modifier.padding(Spacing.medium)
                    )
                },
                showModeToggle = false
            )
        }
    }
}
