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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import invoicer.features.intermediary.presentation.generated.resources.Res
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_continue_cta
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_name_label
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_name_placeholder
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_name_subtitle
import invoicer.features.intermediary.presentation.generated.resources.create_intermediary_name_title
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryScreenModel
import io.github.alaksion.invoicer.foundation.designSystem.components.InputField
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import org.jetbrains.compose.resources.stringResource

internal class IntermediaryNameStep : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<CreateIntermediaryScreenModel>()
        val state by screenModel.state.collectAsState()

        StateContent(
            name = state.name,
            onNameChange = screenModel::updateName,
            buttonEnabled = state.nameIsValid,
            onBack = { navigator.parent?.pop() },
            onContinue = { navigator.push(IntermediaryDocumentStep()) }
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
                    label = stringResource(Res.string.create_intermediary_continue_cta),
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
                    title = stringResource(Res.string.create_intermediary_name_title),
                    subTitle = stringResource(Res.string.create_intermediary_name_subtitle)
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
                            text = stringResource(Res.string.create_intermediary_name_placeholder)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(Res.string.create_intermediary_name_label)
                        )
                    }
                )
            }
        }
    }
}
