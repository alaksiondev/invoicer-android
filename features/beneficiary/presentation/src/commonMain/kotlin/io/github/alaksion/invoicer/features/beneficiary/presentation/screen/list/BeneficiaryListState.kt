package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class BeneficiaryListState(
    val isNextPageLoading: Boolean = false,
    val mode: BeneficiaryListMode = BeneficiaryListMode.Loading,
    val beneficiaries: ImmutableList<BeneficiaryModel> = persistentListOf()
)

internal enum class BeneficiaryListMode {
    Loading,
    Content,
    Error,
}

internal enum class BeneficiaryListEvents {
    LoadMoreError
}

internal interface BeneficiaryListCallbacks {
    fun onClose()
    fun onRetry()
    fun onCreateClick()
    fun onItemClick(id: String)
}

@Composable
internal fun rememberBeneficiaryListCallbacks(
    onClose: () -> Unit,
    onRetry: () -> Unit,
    onCreate: () -> Unit,
    onItemClick: (String) -> Unit
) = remember {
    object : BeneficiaryListCallbacks {
        override fun onClose() = onClose()

        override fun onRetry() = onRetry()

        override fun onCreateClick() = onCreate()

        override fun onItemClick(id: String) = onItemClick(id)
    }
}
