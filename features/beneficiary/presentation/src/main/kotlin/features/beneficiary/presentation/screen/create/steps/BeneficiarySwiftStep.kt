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
import features.beneficiary.presentation.R
import features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import foundation.design.system.tokens.Spacing

internal class BeneficiarySwiftStep : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<CreateBeneficiaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        StateContent(
            swift = state.swift,
            onSwiftChange = screenModel::updateSwift,
            buttonEnabled = state.swiftIsValid,
            onBack = { navigator.pop() },
            onContinue = { navigator.push(BeneficiaryIbanStep()) }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        swift: String,
        buttonEnabled: Boolean,
        onSwiftChange: (String) -> Unit,
        onBack: () -> Unit,
        onContinue: () -> Unit,
    ) {
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
                    modifier = Modifier.fillMaxWidth(),
                    value = swift,
                    onValueChange = onSwiftChange,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboard?.hide() }
                    )
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