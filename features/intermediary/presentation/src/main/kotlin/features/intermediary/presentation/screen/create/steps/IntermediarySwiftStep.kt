package features.intermediary.presentation.screen.create.steps

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
import features.intermediary.presentation.screen.create.components.IntermediaryBaseForm
import features.intermediary.presentation.R
import features.intermediary.presentation.screen.create.CreateIntermediaryScreenModel

internal class IntermediarySwiftStep : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<CreateIntermediaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        StateContent(
            swift = state.swift,
            onSwiftChange = screenModel::updateSwift,
            buttonEnabled = state.swiftIsValid,
            onBack = {
                screenModel.cancelSubmit()
                navigator.pop()
            },
            onContinue = { navigator.push(IntermediaryIbanStep()) }
        )
    }

    @Composable
    fun StateContent(
        swift: String,
        buttonEnabled: Boolean,
        onSwiftChange: (String) -> Unit,
        onBack: () -> Unit,
        onContinue: () -> Unit,
    ) {
        val keyboard = LocalSoftwareKeyboardController.current
        IntermediaryBaseForm(
            title = stringResource(R.string.create_intermediary_swift_title),
            buttonText = stringResource(R.string.create_intermediary_continue_cta),
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
                value = swift,
                onValueChange = onSwiftChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboard?.hide() }
                ),
                label = {
                    Text(
                        text = stringResource(R.string.create_intermediary_swift_label)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.create_intermediary_swift_placeholder)
                    )
                }
            )
            Spacer(1f)
        }
    }
}