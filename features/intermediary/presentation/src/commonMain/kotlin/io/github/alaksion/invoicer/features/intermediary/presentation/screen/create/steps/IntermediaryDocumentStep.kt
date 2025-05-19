package io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.steps

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import invoicer.features.intermediary.presentation.generated.resources.Res
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_continue_cta
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_document_subtitle
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_document_title
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_iban_label
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_iban_placeholder
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_swift_label
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_swift_placeholder
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryScreenModel
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryState
import io.github.alaksion.invoicer.foundation.designSystem.components.InputField
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import org.jetbrains.compose.resources.stringResource

internal class IntermediaryDocumentStep : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<CreateIntermediaryScreenModel>()
        val state by screenModel.state.collectAsState()

        StateContent(
            state = state,
            onSwiftChange = screenModel::updateSwift,
            onIbanChange = screenModel::updateIban,
            buttonEnabled = state.ibanIsValid && state.swiftIsValid,
            onBack = { navigator.pop() },
            onContinue = { navigator.push(IntermediaryBankInfoStep()) }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: CreateIntermediaryState,
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
                    label = stringResource(Res.string.create_intermediary_continue_cta),
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
                    title = stringResource(Res.string.create_intermediary_document_title),
                    subTitle = stringResource(Res.string.create_intermediary_document_subtitle)
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
                            text = stringResource(Res.string.create_intermediary_iban_label)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(Res.string.create_intermediary_iban_placeholder)
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
                            text = stringResource(Res.string.create_intermediary_swift_label)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(Res.string.create_intermediary_swift_placeholder)
                        )
                    },
                )
            }
        }
    }
}
