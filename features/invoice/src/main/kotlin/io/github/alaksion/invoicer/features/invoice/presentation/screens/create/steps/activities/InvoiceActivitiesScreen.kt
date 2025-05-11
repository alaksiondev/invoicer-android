package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import foundation.designsystem.components.SwipeableCard
import foundation.designsystem.components.ScreenTitle
import foundation.designsystem.components.buttons.BackButton
import foundation.designsystem.components.buttons.PrimaryButton
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.Spacing
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.components.InvoiceActivityCard
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.InvoiceActivitiesScreen.TestTags.ADD_ACTIVITY
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.InvoiceActivitiesScreen.TestTags.LIST_ITEM
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.components.AddActivityBottomSheet
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.model.rememberSnackMessages
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.confirmation.InvoiceConfirmationScreen
import io.github.alasion.invoicer.features.invoice.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class InvoiceActivitiesScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<InvoiceActivitiesScreenModel>()
        val navigator = LocalNavigator.current

        ScreenContent(
            screenModel = screenModel,
            onBack = { navigator?.pop() },
            onContinue = { navigator?.push(InvoiceConfirmationScreen()) }
        )
    }

    @Composable
    fun ScreenContent(
        screenModel: InvoiceActivitiesScreenModel,
        onBack: () -> Unit,
        onContinue: () -> Unit
    ) {
        val state by screenModel.state.collectAsStateWithLifecycle()
        val snackbarState = remember { SnackbarHostState() }
        val messages = rememberSnackMessages()
        val scope = rememberCoroutineScope()

        LaunchedEffect(screenModel) {
            screenModel.initState()
        }

        LaunchedEffect(screenModel) {
            screenModel.events.collectLatest {
                when (it) {
                    InvoiceActivitiesEvent.ActivityQuantityError ->
                        scope.launch {
                            snackbarState.showSnackbar(message = messages.quantityError)
                        }

                    InvoiceActivitiesEvent.ActivityUnitPriceError -> scope.launch {
                        snackbarState.showSnackbar(message = messages.unitPriceError)
                    }
                }
            }
        }

        val callbacks = remember {
            InvoiceActivitiesCallbacks(
                onContinue = onContinue,
                onBack = onBack,
                onChangeDescription = screenModel::updateFormDescription,
                onChangeUnitPrice = screenModel::updateFormUnitPrice,
                onChangeQuantity = screenModel::updateFormQuantity,
                onClearForm = screenModel::clearForm,
                onAddActivity = screenModel::addActivity,
                onDelete = screenModel::removeActivity,
            )
        }

        StateContent(
            state = state,
            snackbarHostState = snackbarState,
            callbacks = callbacks
        )
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun StateContent(
        state: InvoiceActivitiesState,
        snackbarHostState: SnackbarHostState,
        callbacks: InvoiceActivitiesCallbacks
    ) {
        val sheetState = rememberModalBottomSheetState()
        var showSheet by remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        BackButton(onBackClick = callbacks.onBack)
                    }
                )
            },
            bottomBar = {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.medium),
                    label = stringResource(R.string.invoice_create_continue_cta),
                    onClick = callbacks.onContinue,
                    isEnabled = state.isButtonEnabled
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) { scaffoldPadding ->
            val verticalScroll = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(Spacing.medium)
                    .verticalScroll(verticalScroll)
            ) {
                ScreenTitle(
                    title = stringResource(R.string.invoice_create_activity_title),
                    subTitle = stringResource(R.string.invoice_create_activity_subtitle)
                )
                VerticalSpacer(SpacerSize.XLarge3)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .testTag(TestTags.LIST),
                    verticalArrangement = Arrangement.spacedBy(Spacing.medium)
                ) {
                    stickyHeader {
                        PrimaryButton(
                            label = stringResource(R.string.invoice_create_activity_add_cta),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag(ADD_ACTIVITY),
                            onClick = {
                                showSheet = true
                            }
                        )
                    }

                    items(
                        items = state.activities,
                        key = { it.id }
                    ) { activity ->
                        var isRevealed by remember { mutableStateOf(false) }
                        SwipeableCard(
                            content = {
                                InvoiceActivityCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag(LIST_ITEM),
                                    quantity = activity.quantity,
                                    description = activity.description,
                                    unitPrice = activity.unitPrice,
                                )
                            },
                            extraContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(Spacing.medium),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(
                                        onClick = {
                                            callbacks.onDelete(activity.id)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            },
                            onExpanded = { isRevealed = true },
                            onCollapsed = { isRevealed = false },
                            modifier = Modifier.fillMaxWidth(),
                            isRevealed = isRevealed
                        )
                    }
                }
                if (showSheet) {
                    AddActivityBottomSheet(
                        sheetState = sheetState,
                        formState = state.formState,
                        onChangeQuantity = callbacks.onChangeQuantity,
                        onChangeUnitPrice = callbacks.onChangeUnitPrice,
                        onChangeDescription = callbacks.onChangeDescription,
                        onDismiss = {
                            showSheet = false
                            callbacks.onClearForm()
                        },
                        onAddActivity = {
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                showSheet = false
                                callbacks.onAddActivity()
                            }
                        }
                    )
                }
            }
        }
    }

    internal object TestTags {
        const val LIST = "add_invoice_activity_list"
        const val ADD_ACTIVITY = "add_invoice_activity_add"
        const val LIST_ITEM = "add_invoice_activity_item"
    }
}
