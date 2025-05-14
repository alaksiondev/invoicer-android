package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates.components.InvoiceDatePicker
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary.PickBeneficiaryScreen
import io.github.alaksion.invoicer.foundation.designSystem.components.InputField
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import io.github.alaksion.invoicer.foundation.utils.date.defaultFormat
import io.github.alasion.invoicer.features.invoice.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Instant

internal class InvoiceDatesStep : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = koinScreenModel<InvoiceDatesScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(screenModel) {
            screenModel.events.collectLatest {
                when (it) {
                    InvoiceDateEvents.Continue -> navigator?.push(PickBeneficiaryScreen())
                }
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
                InputField(
                    value = state.issueDate.defaultFormat(),
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        TextButton(
                            onClick = { isIssueDatePickerVisible = true }
                        ) {
                            Text(text = stringResource(R.string.invoice_create_dates_change_cta))
                        }
                    },
                    readOnly = true,
                    label = {
                        Text(
                            text = stringResource(R.string.invoice_create_dates_issue_date_label)
                        )
                    }
                )
                VerticalSpacer(SpacerSize.XLarge3)
                InputField(
                    value = state.dueDate.defaultFormat(),
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        TextButton(
                            onClick = { isDueDatePickerVisible = true }
                        ) {
                            Text(text = stringResource(R.string.invoice_create_dates_change_cta))
                        }
                    },
                    readOnly = true,
                    label = {
                        Text(
                            text = stringResource(R.string.invoice_create_dates_due_date_label)
                        )
                    },
                    isError = state.dueDateValid.not(),
                    supportingText = {
                        if (state.dueDateValid.not()) {
                            Text(
                                text = stringResource(R.string.invoice_create_dates_due_date_error)
                            )
                        }
                    }
                )
            }
        }

        InvoiceDatePicker(
            isVisible = isIssueDatePickerVisible,
            title = stringResource(R.string.invoice_issue_date_dialog_title),
            selectedDate = state.issueDate,
            minimumDate = state.now,
            onSelectDate = { newDate ->
                onSetIssueDate(newDate)
                isIssueDatePickerVisible = false
            },
            onDismissRequest = {
                isIssueDatePickerVisible = false
            }
        )

        InvoiceDatePicker(
            isVisible = isDueDatePickerVisible,
            title = stringResource(R.string.invoice_due_date_dialog_title),
            selectedDate = state.dueDate,
            minimumDate = state.issueDate,
            onSelectDate = { newDate ->
                onSetDueDate(newDate)
                isDueDatePickerVisible = false
            },
            onDismissRequest = {
                isDueDatePickerVisible = false
            }
        )
    }
}
