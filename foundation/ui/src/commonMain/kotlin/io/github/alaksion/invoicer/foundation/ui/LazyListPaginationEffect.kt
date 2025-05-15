package io.github.alaksion.invoicer.foundation.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun LazyListPaginationEffect(
    offset: Int = 0,
    state: LazyListState,
    enabled: Boolean,
    onPaginationTrigger: () -> Unit,
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val itemCount = state.layoutInfo.totalItemsCount
            val lastVisibleIndex = state.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            (lastVisibleIndex >= (itemCount - offset)) && enabled
        }
    }

    LaunchedEffect(state) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            // Only call trigger when content reaches the bottom
            .filter { it }
            .collect { onPaginationTrigger() }
    }
}
