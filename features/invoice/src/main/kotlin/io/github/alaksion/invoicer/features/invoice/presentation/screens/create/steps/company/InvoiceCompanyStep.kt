package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component3
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component4
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import foundation.designsystem.components.InputField
import foundation.designsystem.components.ScreenTitle
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.components.buttons.PrimaryButton
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.theme.InvoicerTheme
import foundation.designsystem.tokens.Spacing
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates.InvoiceDatesStep
import io.github.alasion.invoicer.features.invoice.R

internal class InvoiceCompanyStep : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<InvoiceCompanyScreenModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current

        LaunchedEffect(viewModel) {
            viewModel.events.collect {
                navigator?.push(InvoiceDatesStep())
            }
        }

        StateContent(
            onBack = { navigator?.pop() },
            state = state.value,
            onChangeSenderName = viewModel::onSenderNameChange,
            onChangeSenderAddress = viewModel::onSenderAddressChange,
            onChangeRecipientName = viewModel::onRecipientNameChange,
            onChangeRecipientAddress = viewModel::onRecipientAddressChange,
            onSubmit = viewModel::submit
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        onBack: () -> Unit,
        onSubmit: () -> Unit,
        onChangeSenderName: (String) -> Unit,
        onChangeSenderAddress: (String) -> Unit,
        onChangeRecipientName: (String) -> Unit,
        onChangeRecipientAddress: (String) -> Unit,
        state: InvoiceCompanyState
    ) {
        val (senderName, senderAddress) = FocusRequester.createRefs()
        val (recipientName, recipientAddress) = FocusRequester.createRefs()
        val keyboard = LocalSoftwareKeyboardController.current

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
                    label = stringResource(R.string.invoice_create_continue_cta),
                    onClick = onSubmit,
                    isEnabled = state.isButtonEnabled
                )
            }
        ) { scaffoldPadding ->
            val verticalScroll = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(Spacing.medium)
                    .verticalScroll(verticalScroll)
            ) {
                ScreenTitle(
                    title = stringResource(R.string.invoice_company_title),
                    subTitle = stringResource(R.string.invoice_company_description)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(senderName),
                    label = {
                        Text(stringResource(R.string.invoice_create_sender_company_name_label))
                    },
                    placeholder = {
                        Text(stringResource(R.string.invoice_create_sender_company_name_placeholder))
                    },
                    value = state.senderName,
                    onValueChange = onChangeSenderName,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            senderAddress.requestFocus()
                        }
                    )
                )
                VerticalSpacer(SpacerSize.Medium)
                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(senderAddress),
                    label = {
                        Text(stringResource(R.string.invoice_create_sender_company_address_label))
                    },
                    placeholder = {
                        Text(stringResource(R.string.invoice_create_sender_company_address_placeholder))
                    },
                    value = state.senderAddress,
                    onValueChange = onChangeSenderAddress,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            recipientName.requestFocus()
                        }
                    )
                )
                VerticalSpacer(SpacerSize.XLarge3)
                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(recipientName),
                    label = {
                        Text(stringResource(R.string.invoice_create_recipient_company_name_label))
                    },
                    placeholder = {
                        Text(stringResource(R.string.invoice_create_recipient_company_name_placeholder))
                    },
                    value = state.recipientName,
                    onValueChange = onChangeRecipientName,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            recipientAddress.requestFocus()
                        }
                    )
                )
                VerticalSpacer(SpacerSize.Medium)
                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(recipientAddress),
                    label = {
                        Text(stringResource(R.string.invoice_create_recipient_company_address_label))
                    },
                    placeholder = {
                        Text(stringResource(R.string.invoice_create_recipient_company_address_placeholder))
                    },
                    value = state.recipientAddress,
                    onValueChange = onChangeRecipientAddress,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboard?.hide()
                        }
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    InvoicerTheme {
        InvoiceCompanyStep().StateContent(
            onBack = {},
            state = InvoiceCompanyState(),
            onChangeSenderName = {},
            onChangeSenderAddress = {},
            onChangeRecipientName = {},
            onChangeRecipientAddress = {},
            onSubmit = {}
        )
    }
}
