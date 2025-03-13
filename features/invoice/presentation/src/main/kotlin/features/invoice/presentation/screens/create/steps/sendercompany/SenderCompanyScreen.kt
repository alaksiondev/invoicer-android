package features.invoice.presentation.screens.create.steps.sendercompany

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.spacer.Spacer
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.invoice.presentation.R
import features.invoice.presentation.screens.create.components.CreateInvoiceBaseForm
import features.invoice.presentation.screens.create.steps.recipientcompany.RecipientCompanyScreen
import foundation.ui.events.EventEffect

internal class SenderCompanyScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<SenderCompanyScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current

        val callbacks = rememberSenderCompanyCallbacks(
            onBack = { navigator?.pop() },
            onUpdateAddress = screenModel::updateAddress,
            onUpdateName = screenModel::updateName,
            onSubmit = screenModel::submit
        )

        LaunchedEffect(Unit) { screenModel.initScreen() }

        foundation.ui.events.EventEffect(screenModel) {
            when (it) {
                SenderCompanyEvents.Continue -> navigator?.push(RecipientCompanyScreen())
            }
        }

        StateContent(
            state = state,
            callbacks = callbacks
        )
    }

    @Composable
    fun StateContent(
        state: SenderCompanyState,
        callbacks: SenderCompanyCallbacks,
    ) {
        val (nameRef, addressRef) = FocusRequester.createRefs()
        val keyboard = LocalSoftwareKeyboardController.current

        CreateInvoiceBaseForm(
            title = stringResource(R.string.invoice_create_sender_company_title),
            onBack = callbacks::onBack,
            onContinue = callbacks::onSubmit,
            buttonEnabled = state.isFormValid,
            buttonText = stringResource(R.string.invoice_create_continue_cta)
        ) {
            Spacer(1f)
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(nameRef),
                value = state.name,
                onValueChange = callbacks::updateName,
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Business,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    autoCorrectEnabled = false,
                    capitalization = KeyboardCapitalization.Words
                ),
                keyboardActions = KeyboardActions(
                    onNext = { addressRef.requestFocus() }
                ),
                placeholder = {
                    Text(
                        text = stringResource(
                            R.string.invoice_create_sender_company_name_placeholder
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(
                            R.string.invoice_create_sender_company_name_label
                        )
                    )
                }
            )
            VerticalSpacer(SpacerSize.Medium)
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(addressRef),
                value = state.address,
                onValueChange = callbacks::updateAddress,
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Map,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    autoCorrectEnabled = false,
                    capitalization = KeyboardCapitalization.Words
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboard?.hide() }
                ),
                placeholder = {
                    Text(
                        text = stringResource(
                            R.string.invoice_create_sender_company_address_placeholder
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(
                            R.string.invoice_create_sender_company_address_label
                        )
                    )
                }
            )
            Spacer(1f)
        }
    }
}