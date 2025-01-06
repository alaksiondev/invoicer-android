package features.beneficiary.presentation.screen.create.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import features.auth.design.system.components.buttons.BackButton
import features.auth.design.system.components.spacer.Spacer
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.beneficiary.presentation.R
import features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import foundation.design.system.tokens.Spacing

internal class BeneficiaryBankInfoStep: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<CreateBeneficiaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        StateContent(
            address = state.bankAddress,
            bankName = state.bankName,
            onAddressChange = screenModel::updateBankAddress,
            onBankNameChange = screenModel::updateBankName,
            onBack = { navigator.pop() },
            onContinue = { navigator.push(BeneficiaryIbanStep()) },
            buttonEnabled = state.bankInfoIsValid
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        address: String,
        bankName: String,
        buttonEnabled: Boolean,
        onAddressChange: (String) -> Unit,
        onBankNameChange: (String) -> Unit,
        onBack: () -> Unit,
        onContinue: () -> Unit,
    ) {
        val (nameFocus, addressFocus) = FocusRequester.createRefs()
        val keyboard = LocalSoftwareKeyboardController.current

        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        BackButton(
                            onBackClick = onBack
                        )
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(Spacing.medium)
            ) {
                Text(
                    text = stringResource(R.string.create_beneficiary_name_title),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(1f)
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameFocus),
                    value = bankName,
                    onValueChange = onBankNameChange,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { addressFocus.requestFocus() }
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.create_beneficiary_bank_name_label)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.create_beneficiary_bank_name_placeholder)
                        )
                    }
                )
                VerticalSpacer(SpacerSize.Medium)
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameFocus),
                    value = address,
                    onValueChange = onAddressChange,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboard?.hide() }
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.create_beneficiary_bank_address_label)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.create_beneficiary_bank_address_placeholder)
                        )
                    }
                )
                Spacer(1f)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onContinue,
                    enabled = buttonEnabled
                ) {
                    Text(
                        text = stringResource(R.string.create_beneficiary_continue_cta)
                    )
                }
            }
        }
    }
}