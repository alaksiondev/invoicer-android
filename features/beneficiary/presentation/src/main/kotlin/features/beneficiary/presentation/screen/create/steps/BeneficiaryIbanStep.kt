package features.beneficiary.presentation.screen.create.steps

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.spacer.Spacer
import features.beneficiary.presentation.R
import features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import features.beneficiary.presentation.screen.create.components.BeneficiaryBaseForm

internal class BeneficiaryIbanStep : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<CreateBeneficiaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current

        StateContent(
            iban = state.iban,
            onIbanChange = screenModel::updateIban,
            buttonEnabled = state.ibanIsValid,
            onBack = { navigator?.pop() },
            onContinue = { navigator?.push(BeneficiaryBankInfoStep()) }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        iban: String,
        buttonEnabled: Boolean,
        onIbanChange: (String) -> Unit,
        onBack: () -> Unit,
        onContinue: () -> Unit,
    ) {
        val keyboard = LocalSoftwareKeyboardController.current

        BeneficiaryBaseForm(
            title = stringResource(R.string.create_beneficiary_iban_title),
            buttonText = stringResource(R.string.create_beneficiary_continue_cta),
            buttonEnabled = buttonEnabled,
            onBack = onBack,
            onContinue = {
                keyboard?.hide()
                onContinue()
            },
        ) {
            Spacer(1f)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = iban,
                onValueChange = onIbanChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboard?.hide() }
                )
            )
            Spacer(1f)
        }
    }
}