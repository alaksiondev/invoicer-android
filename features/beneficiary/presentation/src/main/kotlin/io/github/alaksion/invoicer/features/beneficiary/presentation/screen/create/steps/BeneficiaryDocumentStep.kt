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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import foundation.designsystem.components.InputField
import foundation.designsystem.components.ScreenTitle
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.components.buttons.PrimaryButton
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.Spacing
import io.github.alaksion.invoicer.features.beneficiary.presentation.R
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.CreateBeneficiaryState

internal class BeneficiaryDocumentStep : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<CreateBeneficiaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        StateContent(
            state = state,
            onSwiftChange = screenModel::updateSwift,
            onIbanChange = screenModel::updateIban,
            buttonEnabled = state.ibanIsValid && state.swiftIsValid,
            onBack = { navigator.pop() },
            onContinue = { navigator.push(BeneficiaryBankInfoStep()) }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: CreateBeneficiaryState,
        buttonEnabled: Boolean,
        onIbanChange: (String) -> Unit,
        onSwiftChange: (String) -> Unit,
        onBack: () -> Unit,
        onContinue: () -> Unit,
    ) {
        val keyboard = LocalSoftwareKeyboardController.current
        val (ibanFocus, swiftFocus) = FocusRequester.createRefs()

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
                    label = stringResource(R.string.create_beneficiary_continue_cta),
                    onClick = {
                        keyboard?.hide()
                        onContinue()
                    },
                    isEnabled = buttonEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.medium),
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
                    title = stringResource(R.string.create_beneficiary_document_title),
                    subTitle = stringResource(R.string.create_beneficiary_document_subtitle)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(ibanFocus),
                    value = state.iban,
                    onValueChange = onIbanChange,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { swiftFocus.requestFocus() }
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.create_beneficiary_iban_label)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.create_beneficiary_iban_placeholder)
                        )
                    },
                )
                VerticalSpacer(SpacerSize.Large)
                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(swiftFocus),
                    value = state.swift,
                    onValueChange = onSwiftChange,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboard?.hide() }
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.create_beneficiary_swift_label)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.create_beneficiary_swift_placeholder)
                        )
                    },
                )
            }
        }
    }
}