package io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import invoicer.features.intermediary.presentation.generated.resources.Res
import invoicer.features.intermediary.presentation.generated.resources.intermediary_list_error_description
import invoicer.features.intermediary.presentation.generated.resources.intermediary_list_error_retry
import invoicer.features.intermediary.presentation.generated.resources.intermediary_list_error_title
import invoicer.features.intermediary.presentation.generated.resources.intermediary_list_subtitle
import invoicer.features.intermediary.presentation.generated.resources.intermediary_list_title
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryFlow
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.details.IntermediaryDetailsScreen
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.components.IntermediaryList
import io.github.alaksion.invoicer.foundation.designSystem.components.LoadingState
import io.github.alaksion.invoicer.foundation.designSystem.components.ScreenTitle
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.CloseButton
import io.github.alaksion.invoicer.foundation.designSystem.components.feedback.Feedback
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import io.github.alaksion.invoicer.foundation.ui.LazyListPaginationEffect
import io.github.alaksion.invoicer.foundation.ui.events.EventEffect
import io.github.alaksion.invoicer.foundation.watchers.RefreshIntermediaryPublisher
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin

internal class IntermediaryListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<IntermediaryListScreenModel>()
        val newBeneficiaryPublisher by remember { getKoin().inject<RefreshIntermediaryPublisher>() }
        val state by viewModel.state.collectAsState()

        val callbacks = rememberIntermediaryListCallbacks(
            onClose = { navigator?.pop() },
            onRetry = viewModel::loadPage,
            onCreate = { navigator?.push(CreateIntermediaryFlow()) },
            onItemClick = { navigator?.push(IntermediaryDetailsScreen(it)) }
        )

        LaunchedEffect(Unit) { viewModel.loadPage() }

        EventEffect(newBeneficiaryPublisher) {
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
        state: IntermediaryListState,
        callbacks: IntermediaryListCallbacks,
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
                if (state.mode == IntermediaryListMode.Content) {
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
                    title = stringResource(Res.string.intermediary_list_title),
                    subTitle = stringResource(Res.string.intermediary_list_subtitle)
                )
                VerticalSpacer(height = SpacerSize.XLarge)

                when (state.mode) {
                    IntermediaryListMode.Loading -> LoadingState(
                        modifier = Modifier.fillMaxSize()
                    )

                    IntermediaryListMode.Content -> {
                        IntermediaryList(
                            items = state.beneficiaries,
                            listState = listState,
                            modifier = Modifier.fillMaxSize(),
                            onItemClick = callbacks::onItemClick,
                            isLoadingMore = state.isNextPageLoading
                        )
                        LazyListPaginationEffect(
                            state = listState,
                            onPaginationTrigger = {},
                            enabled = true,
                        )
                    }

                    IntermediaryListMode.Error -> Feedback(
                        modifier = Modifier.fillMaxSize(),
                        primaryActionText = stringResource(Res.string.intermediary_list_error_retry),
                        onPrimaryAction = callbacks::onRetry,
                        icon = Icons.Outlined.ErrorOutline,
                        title = stringResource(Res.string.intermediary_list_error_title),
                        description = stringResource(Res.string.intermediary_list_error_description)
                    )
                }
            }
        }
    }
}
