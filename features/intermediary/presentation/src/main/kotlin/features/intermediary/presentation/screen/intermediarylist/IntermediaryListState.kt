package features.intermediary.presentation.screen.intermediarylist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import features.intermediary.domain.model.IntermediaryModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class IntermediaryListState(
    val isNextPageLoading: Boolean = false,
    val mode: IntermediaryListMode = IntermediaryListMode.Loading,
    val beneficiaries: ImmutableList<IntermediaryModel> = persistentListOf()
)

internal enum class IntermediaryListMode {
    Loading,
    Content,
    Error,
}

internal interface IntermediaryListCallbacks {
    fun onClose()
    fun onRetry()
    fun onCreateClick()
    fun onItemClick(id: String)
}

@Composable
internal fun rememberIntermediaryListCallbacks(
    onClose: () -> Unit,
    onRetry: () -> Unit,
    onCreate: () -> Unit,
    onItemClick: (String) -> Unit
) = remember {
    object : IntermediaryListCallbacks {
        override fun onClose() = onClose()

        override fun onRetry() = onRetry()

        override fun onCreateClick() = onCreate()

        override fun onItemClick(id: String) = onItemClick(id)
    }
}
