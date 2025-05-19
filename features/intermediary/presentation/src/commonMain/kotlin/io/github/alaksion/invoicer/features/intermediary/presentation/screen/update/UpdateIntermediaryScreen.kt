package io.github.alaksion.invoicer.features.intermediary.presentation.screen.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component3
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import invoicer.features.intermediary.presentation.generated.resources.Res
import invoicer.features.intermediary.presentation.generated.resources.intermediary_update_bank_address_label
import invoicer.features.intermediary.presentation.generated.resources.intermediary_update_bank_name_label
import invoicer.features.intermediary.presentation.generated.resources.intermediary_update_cta
import invoicer.features.intermediary.presentation.generated.resources.intermediary_update_iban_label
import invoicer.features.intermediary.presentation.generated.resources.intermediary_update_load_error_cta
import invoicer.features.intermediary.presentation.generated.resources.intermediary_update_load_error_description
import invoicer.features.intermediary.presentation.generated.resources.intermediary_update_load_error_title
import invoicer.features.intermediary.presentation.generated.resources.intermediary_update_name_label
import invoicer.features.intermediary.presentation.generated.resources.intermediary_update_swift_label
import invoicer.features.intermediary.presentation.generated.resources.update_intermediary_title
import io.github.alaksion.invoicer.foundation.designSystem.components.InputField
import io.github.alaksion.invoicer.foundation.designSystem.components.LoadingState
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.BackButton
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.feedback.Feedback
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

internal data class UpdateIntermediaryScreen(
    private val id: String
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<UpdateIntermediaryScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.current
        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) { screenModel.initState(id) }

        LaunchedEffect(screenModel) {
            screenModel.events.collectLatest {
                when (it) {
                    is UpdateIntermediaryEvent.Error ->
                        scope.launch { snackBarHostState.showSnackbar(it.message) }

                    UpdateIntermediaryEvent.Success -> navigator?.pop()
                }
            }
        }

        val callBacks = remember {
            UpdateIntermediaryCallbacks(
                onBack = { navigator?.pop() },
                onChangeName = screenModel::updateName,
                onChangeBankName = screenModel::updateBankName,
                onChangeBankAddress = screenModel::updateBankAddress,
                onChangeSwift = screenModel::updateSwift,
                onChangeIban = screenModel::updateIban,
                onSubmit = {
                    screenModel.submit(id)
                },
                onRetry = {
                    screenModel.initState(id)
                }
            )
        }

        StateContent(
            state = state,
            callbacks = callBacks,
            snackbarHostState = snackBarHostState
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: UpdateIntermediaryState,
        snackbarHostState: SnackbarHostState,
        callbacks: UpdateIntermediaryCallbacks,
    ) {
        val scrollState = rememberScrollState()

        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    navigationIcon = { BackButton(onBackClick = callbacks.onBack) },
                    title = { }
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            },
            bottomBar = {
                PrimaryButton(
                    onClick = callbacks.onSubmit,
                    isEnabled = state.isButtonEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.medium),
                    label = stringResource(Res.string.intermediary_update_cta),
                    isLoading = state.isButtonLoading
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(Spacing.medium)
            ) {

                when (state.mode) {
                    UpdateIntermediaryMode.Loading -> LoadingState(
                        modifier = Modifier.fillMaxSize()
                    )

                    UpdateIntermediaryMode.Content -> {
                        val (nameRef, bankNameRef, bankAddressRef) = FocusRequester.createRefs()
                        val (swiftRef, ibanRef) = FocusRequester.createRefs()

                        val keyboard = LocalSoftwareKeyboardController.current
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                        ) {
                            ScreenTitle(
                                title = stringResource(Res.string.update_intermediary_title),
                                subTitle = null
                            )
                            VerticalSpacer(SpacerSize.XLarge3)

                            InputField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(nameRef),
                                value = state.name,
                                onValueChange = callbacks.onChangeName,
                                label = {
                                    Text(
                                        text = stringResource(Res.string.intermediary_update_name_label)
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
                                onValueChange = callbacks.onChangeBankName,
                                label = {
                                    Text(
                                        text = stringResource(Res.string.intermediary_update_bank_name_label)
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

                            InputField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(bankAddressRef),
                                value = state.bankAddress,
                                onValueChange = callbacks.onChangeBankAddress,
                                label = {
                                    Text(
                                        text = stringResource(Res.string.intermediary_update_bank_address_label)
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

                            InputField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(swiftRef),
                                value = state.swift,
                                onValueChange = callbacks.onChangeSwift,
                                label = {
                                    Text(
                                        text = stringResource(Res.string.intermediary_update_swift_label)
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

                            InputField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(ibanRef),
                                value = state.iban,
                                onValueChange = callbacks.onChangeIban,
                                label = {
                                    Text(
                                        text = stringResource(Res.string.intermediary_update_iban_label)
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
                        }
                    }

                    UpdateIntermediaryMode.Error -> Feedback(
                        title = stringResource(Res.string.intermediary_update_load_error_title),
                        description = stringResource(Res.string.intermediary_update_load_error_description),
                        primaryActionText = stringResource(Res.string.intermediary_update_load_error_cta),
                        onPrimaryAction = callbacks.onRetry,
                        modifier = Modifier.fillMaxSize(),
                        icon = Icons.Outlined.ErrorOutline
                    )
                }
            }
        }
    }
}
