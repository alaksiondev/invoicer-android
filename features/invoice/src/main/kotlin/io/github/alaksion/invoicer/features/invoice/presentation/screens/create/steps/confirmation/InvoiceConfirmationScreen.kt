package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.confirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AlarmOn
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.components.InvoiceActivityCard
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.confirmation.components.ConfirmationCard
import io.github.alaksion.invoicer.features.invoice.presentation.screens.feedback.InvoiceFeedbackScreen
import io.github.alasion.invoicer.features.invoice.R
import kotlinx.coroutines.launch

internal class InvoiceConfirmationScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<InvoiceConfirmationScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) { screenModel.initState() }

        EventEffect(screenModel) {
            when (it) {
                is InvoiceConfirmationEvent.Error -> scope.launch {
                    snackbarHostState.showSnackbar(it.message)
                }

                InvoiceConfirmationEvent.Success -> navigator?.push(InvoiceFeedbackScreen())
            }
        }

        StateContent(
            state = state,
            snackbarHostState = snackbarHostState,
            onBack = {},
            onSubmit = screenModel::submitInvoice
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: InvoiceConfirmationState,
        snackbarHostState: SnackbarHostState,
        onSubmit: () -> Unit,
        onBack: () -> Unit,
    ) {
        Scaffold(
            modifier = Modifier.imePadding(),
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
                    label = stringResource(R.string.invoice_create_confirmation_continue_cta),
                    onClick = onSubmit,
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(Spacing.medium)
            ) {
                ScreenTitle(
                    title = stringResource(R.string.invoice_create_confirmation_title),
                    subTitle = stringResource(R.string.invoice_create_confirmation_subtitle)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                ) {
                    item {
                        ConfirmationCard(
                            label = stringResource(R.string.confirmation_external_id_label),
                            content = state.externalId,
                            icon = Icons.Outlined.Badge
                        )
                    }
                    item {
                        ConfirmationCard(
                            label = stringResource(R.string.confirmation_sender_company_name_label),
                            content = state.senderCompanyName,
                            icon = Icons.Outlined.Business
                        )
                    }
                    item {
                        ConfirmationCard(
                            label = stringResource(R.string.confirmation_sender_company_name_address),
                            content = state.senderCompanyAddress,
                            icon = Icons.Outlined.Business
                        )
                    }
                    item {
                        ConfirmationCard(
                            label = stringResource(R.string.confirmation_recipient_company_name_label),
                            content = state.recipientCompanyName,
                            icon = Icons.Outlined.Business
                        )
                    }
                    item {
                        ConfirmationCard(
                            label = stringResource(R.string.confirmation_recipient_company_name_address),
                            content = state.recipientCompanyAddress,
                            icon = Icons.Outlined.Business
                        )
                    }
                    item {
                        ConfirmationCard(
                            label = stringResource(R.string.confirmation_issue_date_label),
                            content = state.issueDate,
                            icon = Icons.Outlined.Alarm
                        )
                    }
                    item {
                        ConfirmationCard(
                            label = stringResource(R.string.confirmation_due_date_label),
                            content = state.dueDate,
                            icon = Icons.Outlined.AlarmOn
                        )
                    }
                    item {
                        ConfirmationCard(
                            label = stringResource(R.string.confirmation_beneficiary_label),
                            content = state.beneficiaryName,
                            icon = Icons.Outlined.AccountBalance
                        )
                    }

                    item {
                        ConfirmationCard(
                            label = stringResource(R.string.confirmation_amount_label),
                            content = state.totalAmount,
                            icon = Icons.Outlined.AttachMoney
                        )
                    }

                    item {
                        HorizontalDivider()
                    }

                    item {
                        Text(
                            text = stringResource(R.string.confirmation_activities_label),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    items(
                        items = state.activities
                    ) {
                        InvoiceActivityCard(
                            modifier = Modifier.fillMaxWidth(),
                            quantity = it.quantity,
                            unitPrice = it.unitPrice,
                            description = it.description
                        )
                    }
                }
            }
        }
    }
}