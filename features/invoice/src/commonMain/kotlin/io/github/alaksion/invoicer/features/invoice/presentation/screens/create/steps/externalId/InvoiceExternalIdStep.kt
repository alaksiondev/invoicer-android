package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import invoicer.features.invoice.generated.resources.Res
import invoicer.features.invoice.generated.resources.invoice_create_continue_cta
import invoicer.features.invoice.generated.resources.invoice_create_external_id_description
import invoicer.features.invoice.generated.resources.invoice_create_external_id_label
import invoicer.features.invoice.generated.resources.invoice_create_external_id_placeholder
import invoicer.features.invoice.generated.resources.invoice_create_external_id_title
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company.InvoiceCompanyStep
import io.github.alaksion.invoicer.foundation.designSystem.components.InputField
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource

internal class InvoiceExternalIdStep : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<InvoiceExternalIdScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.current

        LaunchedEffect(screenModel) {
            screenModel.events.collectLatest {
                navigator?.push(InvoiceCompanyStep())
            }
        }

        StateContent(
            onBack = { navigator?.pop() },
            onSubmit = screenModel::submit,
            state = state,
            onUpdate = screenModel::updateExternalId
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        onSubmit: () -> Unit,
        onUpdate: (String) -> Unit,
        onBack: () -> Unit,
        state: InvoiceExternalIdState
    ) {
        val keyboard = LocalSoftwareKeyboardController.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = { BackButton(onBackClick = onBack) }
                )
            },
            bottomBar = {
                PrimaryButton(
                    label = stringResource(Res.string.invoice_create_continue_cta),
                    onClick = onSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.medium),
                    isEnabled = state.isButtonEnabled
                )
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(Spacing.medium)
            ) {
                ScreenTitle(
                    title = stringResource(Res.string.invoice_create_external_id_title),
                    subTitle = stringResource(Res.string.invoice_create_external_id_description)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.externalId,
                    onValueChange = onUpdate,
                    maxLines = 1,
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
                                Res.string.invoice_create_external_id_placeholder
                            )
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(
                                Res.string.invoice_create_external_id_label
                            )
                        )
                    }
                )
            }
        }
    }
}
