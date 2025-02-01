package features.intermediary.presentation.screen.intermediarylist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.design.system.components.buttons.CloseButton
import features.auth.design.system.components.feedback.Feedback
import features.intermediary.presentation.R
import features.intermediary.presentation.screen.create.CreateIntermediaryFlow
import features.intermediary.presentation.screen.details.IntermediaryDetailsScreen
import features.intermediary.presentation.screen.intermediarylist.components.IntermediaryList
import features.intermediary.publisher.RefreshIntermediaryPublisher
import foundation.design.system.tokens.Spacing
import foundation.events.EventEffect
import foundation.pagination.LazyListPaginationEffect
import org.koin.java.KoinJavaComponent.getKoin

internal class IntermediaryListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinScreenModel<IntermediaryListScreenModel>()
        val newBeneficiaryPublisher by remember { getKoin().inject<RefreshIntermediaryPublisher>() }
        val state by viewModel.state.collectAsStateWithLifecycle()

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
            topBar = {
                MediumTopAppBar(
                    title = { Text(stringResource(R.string.intermediary_list_title)) },
                    navigationIcon = { CloseButton(onBackClick = callbacks::onClose) },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = callbacks::onCreateClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null
                    )
                }
            }
        ) {
            when (state.mode) {
                IntermediaryListMode.Loading -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                IntermediaryListMode.Content -> {
                    IntermediaryList(
                        items = state.beneficiaries,
                        listState = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(Spacing.medium),
                    primaryActionText = stringResource(R.string.intermediary_list_error_retry),
                    onPrimaryAction = callbacks::onRetry,
                    icon = Icons.Outlined.ErrorOutline,
                    title = stringResource(R.string.intermediary_list_error_title),
                    description = stringResource(R.string.intermediary_list_error_description)
                )
            }
        }
    }
}