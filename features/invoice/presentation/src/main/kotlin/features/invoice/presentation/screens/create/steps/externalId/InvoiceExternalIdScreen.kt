package features.invoice.presentation.screens.create.steps.externalId

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import foundation.designsystem.components.spacer.Spacer
import features.invoice.presentation.R
import features.invoice.presentation.screens.create.components.CreateInvoiceBaseForm
import features.invoice.presentation.screens.create.steps.sendercompany.SenderCompanyScreen

internal class InvoiceExternalIdScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<InvoiceExternalIdScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current

        foundation.ui.events.EventEffect(screenModel) {
            when (it) {
                InvoiceExternalIdEvents.Continue -> navigator?.push(SenderCompanyScreen())
            }
        }

        StateContent(
            onBack = { navigator?.pop() },
            onSubmit = screenModel::submit,
            state = state,
            onUpdate = screenModel::updateExternalId
        )
    }

    @Composable
    fun StateContent(
        onSubmit: () -> Unit,
        onUpdate: (String) -> Unit,
        onBack: () -> Unit,
        state: InvoiceExternalIdState
    ) {
        val keyboard = LocalSoftwareKeyboardController.current

        CreateInvoiceBaseForm(
            title = stringResource(R.string.invoice_create_external_id_title),
            buttonText = stringResource(R.string.invoice_create_continue_cta),
            buttonEnabled = state.isEnabled,
            onBack = onBack,
            onContinue = onSubmit,
        ) {
            Spacer(1f)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.externalId,
                onValueChange = onUpdate,
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.QrCode2,
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
                            R.string.invoice_create_external_id_placeholder
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(
                            R.string.invoice_create_external_id_label
                        )
                    )
                }
            )
            Spacer(1f)
        }
    }
}