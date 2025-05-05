package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
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
import foundation.designsystem.components.ScreenTitle
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.components.buttons.PrimaryButton
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.Spacing
import foundation.ui.events.EventEffect
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates.components.MinSelectableDate
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary.PickBeneficiaryScreen
import io.github.alaksion.invoicer.foundation.utils.date.toZeroHour
import io.github.alasion.invoicer.features.invoice.R
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

internal class InvoiceDatesScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = koinScreenModel<InvoiceDatesScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        EventEffect(screenModel) {
            when (it) {
                InvoiceDateEvents.Continue -> navigator?.push(PickBeneficiaryScreen())
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: InvoiceDatesState,
        onSetDueDate: (Instant) -> Unit,
        onSetIssueDate: (Instant) -> Unit,
        onContinue: () -> Unit,
        onBack: () -> Unit
    ) {
        var isIssueDatePickerVisible by remember {
            mutableStateOf(false)
        }

        var isDueDatePickerVisible by remember {
            mutableStateOf(false)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        BackButton(onBackClick = onBack)
                    }
                )
            },
            bottomBar = {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.medium),
                    label = stringResource(R.string.invoice_create_continue_cta),
                    onClick = onContinue,
                    isEnabled = state.formValid
                )
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(Spacing.medium)
            ) {
                ScreenTitle(
                    title = stringResource(R.string.invoice_create_dates_title),
                    subTitle = stringResource(R.string.invoice_create_dates_description)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                isIssueDatePickerVisible = true
                            }
                        ),
                    text = state.dueDate.toString(),
                )
            }
        }

        if (isIssueDatePickerVisible) {
            val pickerState = rememberDatePickerState(
                initialSelectedDateMillis = state.issueDate
                    .toZeroHour(TimeZone.UTC)
                    .toEpochMilliseconds(),
                selectableDates = MinSelectableDate(
                    minYear = state.now.toLocalDateTime(TimeZone.currentSystemDefault()).year,
                    minEpoch = state.now.toZeroHour(TimeZone.UTC).toEpochMilliseconds()
                )
            )
            DatePickerDialog(
                onDismissRequest = {
                    isIssueDatePickerVisible = false
                },
                confirmButton = {
                    PrimaryButton(
                        label = stringResource(R.string.invoice_create_dates_dialog_confirm),
                        onClick = {
                            pickerState.selectedDateMillis?.let { selectedDate ->
                                val selectedInstant = Instant.fromEpochMilliseconds(selectedDate)
                                onSetIssueDate(
                                    selectedInstant
                                        .toLocalDateTime(TimeZone.UTC)
                                        .toInstant(TimeZone.currentSystemDefault())
                                )
                            }
                            isIssueDatePickerVisible = false
                        }
                    )
                }
            ) {
                DatePicker(
                    title = {
                        Text(
                            text = stringResource(R.string.invoice_issue_date_dialog_title),
                            modifier = Modifier.padding(Spacing.medium)
                        )
                    },
                    state = pickerState,
                    showModeToggle = false
                )
            }
        }

        if (isDueDatePickerVisible) {
            val pickerState = rememberDatePickerState(
                initialSelectedDateMillis = state.dueDate
                    .toZeroHour(TimeZone.UTC)
                    .toEpochMilliseconds(),
                selectableDates = MinSelectableDate(
                    minYear = state.now.toLocalDateTime(TimeZone.currentSystemDefault()).year,
                    minEpoch = state.issueDate.toZeroHour(TimeZone.UTC).toEpochMilliseconds()
                )
            )
            DatePickerDialog(
                onDismissRequest = {
                    isDueDatePickerVisible = false
                },
                confirmButton = {
                    PrimaryButton(
                        label = stringResource(R.string.invoice_create_dates_dialog_confirm),
                        onClick = {
                            pickerState.selectedDateMillis?.let { selectedDate ->
                                val selectedInstant = Instant.fromEpochMilliseconds(selectedDate)
                                onSetDueDate(
                                    selectedInstant
                                        .toLocalDateTime(TimeZone.UTC)
                                        .toInstant(TimeZone.currentSystemDefault())
                                )
                            }
                            isDueDatePickerVisible = false
                        }
                    )
                }
            ) {
                DatePicker(
                    state = pickerState,
                    title = {
                        Text(
                            text = stringResource(R.string.invoice_due_date_dialog_title),
                            modifier = Modifier.padding(Spacing.medium)
                        )
                    },
                    showModeToggle = false
                )
            }
        }
    }
}