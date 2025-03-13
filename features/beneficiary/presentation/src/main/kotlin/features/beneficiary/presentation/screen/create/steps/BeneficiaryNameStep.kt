package features.beneficiary.presentation.screen.create.steps

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import foundation.designsystem.components.spacer.Spacer
import features.beneficiary.presentation.R
import features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import features.beneficiary.presentation.screen.create.components.BeneficiaryBaseForm

internal class BeneficiaryNameStep : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<CreateBeneficiaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        StateContent(
            name = state.name,
            onNameChange = screenModel::updateName,
            buttonEnabled = state.nameIsValid,
            onBack = { navigator.parent?.pop() },
            onContinue = { navigator.push(BeneficiarySwiftStep()) }
        )
    }

    @Composable
    fun StateContent(
        name: String,
        buttonEnabled: Boolean,
        onNameChange: (String) -> Unit,
        onBack: () -> Unit,
        onContinue: () -> Unit,
    ) {
        val keyboard = LocalSoftwareKeyboardController.current

        BeneficiaryBaseForm(
            title = stringResource(R.string.create_beneficiary_name_title),
            buttonText = stringResource(R.string.create_beneficiary_continue_cta),
            buttonEnabled = buttonEnabled,
            onContinue = {
                keyboard?.hide()
                onContinue()
            },
            onBack = onBack
        ) {
            Spacer(1f)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = onNameChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboard?.hide() }
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.create_beneficiary_name_placeholder)
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.create_beneficiary_name_label)
                    )
                }
            )
            Spacer(1f)
        }
    }
}