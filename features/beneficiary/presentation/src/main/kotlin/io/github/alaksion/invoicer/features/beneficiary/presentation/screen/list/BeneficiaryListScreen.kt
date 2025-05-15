package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import foundation.navigation.InvoicerScreen
import foundation.ui.LazyListPaginationEffect
import foundation.watchers.RefreshBeneficiaryPublisher
import io.github.alaksion.invoicer.features.beneficiary.presentation.R
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details.BeneficiaryDetailsScreen
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list.components.BeneficiaryList
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.CloseButton
import io.github.alaksion.invoicer.foundation.designSystem.components.feedback.Feedback
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import org.koin.mp.KoinPlatform.getKoin

internal class BeneficiaryListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<BeneficiaryListScreenModel>()
        val refreshBeneficiaryPublisher by remember { getKoin().inject<RefreshBeneficiaryPublisher>() }
        val state by viewModel.state.collectAsStateWithLifecycle()

        val callbacks = rememberBeneficiaryListCallbacks(
            onClose = { navigator?.pop() },
            onRetry = viewModel::loadPage,
            onCreate = {
                navigator?.push(
                    ScreenRegistry.get(
                        InvoicerScreen.Beneficiary.Create
                    )
                )
            },
            onItemClick = { navigator?.push(BeneficiaryDetailsScreen(it)) }
        )

        LaunchedEffect(Unit) { viewModel.loadPage() }

        foundation.ui.events.EventEffect(refreshBeneficiaryPublisher) {
            viewModel.loadPage(force = true)
        }

        StateContent(
            state = state,
            callbacks = callbacks
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: BeneficiaryListState,
        callbacks: BeneficiaryListCallbacks,
    ) {
        val listState = rememberLazyListState()

        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        CloseButton(onBackClick = callbacks::onClose)
                    }
                )
            },
            floatingActionButton = {
                if (state.mode == BeneficiaryListMode.Content) {
                    FloatingActionButton(
                        onClick = callbacks::onCreateClick
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = null
                        )
                    }
                }
            }
        ) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Spacing.medium)
                    .padding(scaffoldPadding)
            ) {
                ScreenTitle(
                    title = stringResource(R.string.beneficiary_list_title),
                    subTitle = stringResource(R.string.beneficiary_list_subtitle)
                )
                VerticalSpacer(height = SpacerSize.XLarge)

                when (state.mode) {
                    BeneficiaryListMode.Loading -> Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(scaffoldPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                    BeneficiaryListMode.Content -> {
                        BeneficiaryList(
                            items = state.beneficiaries,
                            listState = listState,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            onItemClick = callbacks::onItemClick,
                            isLoadingMore = state.isNextPageLoading
                        )
                        LazyListPaginationEffect(
                            state = listState,
                            onPaginationTrigger = {},
                            enabled = true,
                        )
                    }

                    BeneficiaryListMode.Error -> Feedback(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        primaryActionText = stringResource(R.string.beneficiary_list_error_retry),
                        onPrimaryAction = callbacks::onRetry,
                        icon = Icons.Outlined.ErrorOutline,
                        title = stringResource(R.string.beneficiary_list_error_title),
                        description = stringResource(R.string.beneficiary_list_error_description)
                    )
                }
            }
        }
    }
}
