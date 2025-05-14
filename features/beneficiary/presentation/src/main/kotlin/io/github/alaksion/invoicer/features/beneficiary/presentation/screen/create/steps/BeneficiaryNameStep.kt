package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import io.github.alaksion.invoicer.features.beneficiary.presentation.R
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import io.github.alaksion.invoicer.foundation.designSystem.components.InputField
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing

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
            onContinue = { navigator.push(BeneficiaryDocumentStep()) }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        name: String,
        buttonEnabled: Boolean,
        onNameChange: (String) -> Unit,
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
                        BackButton(onBackClick = onBack)
                    }
                )
            },
            bottomBar = {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.medium),
                    label = stringResource(R.string.create_beneficiary_continue_cta),
                    onClick = {
                        keyboard?.hide()
                        onContinue()
                    },
                    isEnabled = buttonEnabled
                )
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Spacing.medium)
                    .padding(scaffoldPadding)
            ) {
                ScreenTitle(
                    title = stringResource(R.string.create_beneficiary_name_title),
                    subTitle = stringResource(R.string.create_beneficiary_name_subtitle)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                InputField(
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
            }
        }
    }
}
