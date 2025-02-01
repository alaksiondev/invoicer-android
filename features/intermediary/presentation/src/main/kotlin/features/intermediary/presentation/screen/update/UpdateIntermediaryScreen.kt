package features.intermediary.presentation.screen.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.buttons.BackButton
import features.auth.design.system.components.spacer.Spacer
import features.intermediary.presentation.R
import foundation.design.system.tokens.Spacing
import foundation.events.EventEffect
import kotlinx.coroutines.launch

internal data class UpdateIntermediaryScreen(
    private val id: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<UpdateIntermediaryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) { screenModel.initState(id) }

        EventEffect(screenModel) {
            when (it) {
                is UpdateIntermediaryEvent.Error ->
                    scope.launch { snackbarHostState.showSnackbar(it.message) }

                UpdateIntermediaryEvent.Success -> navigator?.pop()
            }
        }

        StateContent(
            state = state,
            onBack = { navigator?.pop() },
            onChangeName = screenModel::updateName,
            onChangeBankName = screenModel::updateBankName,
            onChangeBankAddress = screenModel::updateBankAddress,
            onChangeSwift = screenModel::updateSwift,
            onChangeIban = screenModel::updateIban,
            onSubmit = {
                screenModel.submit(id)
            },
            snackbarHostState = snackbarHostState
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: UpdateIntermediaryState,
        snackbarHostState: SnackbarHostState,
        onBack: () -> Unit,
        onChangeName: (String) -> Unit,
        onChangeBankName: (String) -> Unit,
        onChangeBankAddress: (String) -> Unit,
        onChangeSwift: (String) -> Unit,
        onChangeIban: (String) -> Unit,
        onSubmit: () -> Unit
    ) {
        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    navigationIcon = { BackButton(onBackClick = onBack) },
                    title = { Text(text = stringResource(R.string.update_intermediary_title)) }
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) {
            val (
                nameRef, bankNameRef, bankAddressRef, swiftRef, ibanRef
            ) = FocusRequester.createRefs()

            val keyboard = LocalSoftwareKeyboardController.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(Spacing.medium),
                verticalArrangement = Arrangement.spacedBy(Spacing.medium)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameRef),
                    value = state.name,
                    onValueChange = onChangeName,
                    label = {
                        Text(
                            text = stringResource(R.string.intermediary_update_name_label)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { bankNameRef.requestFocus() }
                    ),
                    maxLines = 1
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(bankNameRef),
                    value = state.bankName,
                    onValueChange = onChangeBankName,
                    label = {
                        Text(
                            text = stringResource(R.string.intermediary_update_bank_name_label)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { bankAddressRef.requestFocus() }
                    ),
                    maxLines = 1
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(bankAddressRef),
                    value = state.bankAddress,
                    onValueChange = onChangeBankAddress,
                    label = {
                        Text(
                            text = stringResource(R.string.intermediary_update_bank_address_label)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { swiftRef.requestFocus() }
                    ),
                    maxLines = 1
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(swiftRef),
                    value = state.swift,
                    onValueChange = onChangeSwift,
                    label = {
                        Text(
                            text = stringResource(R.string.intermediary_update_swift_label)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { ibanRef.requestFocus() }
                    ),
                    maxLines = 1
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(ibanRef),
                    value = state.iban,
                    onValueChange = onChangeIban,
                    label = {
                        Text(
                            text = stringResource(R.string.intermediary_update_iban_label)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboard?.hide() }
                    ),
                    maxLines = 1
                )

                Spacer(1f)

                Button(
                    onClick = onSubmit,
                    enabled = state.isButtonEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.intermediary_update_cta))
                }
            }
        }
    }
}