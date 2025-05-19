package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import invoicer.features.beneficiary.presentation.generated.resources.Res
import invoicer.features.beneficiary.presentation.generated.resources.beneficiary_list_empty_description
import invoicer.features.beneficiary.presentation.generated.resources.beneficiary_list_empty_title
import io.github.alaksion.invoicer.features.beneficiary.services.domain.model.BeneficiaryModel
import io.github.alaksion.invoicer.foundation.designSystem.components.emptystate.EmptyState
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BeneficiaryList(
    items: ImmutableList<BeneficiaryModel>,
    listState: LazyListState,
    isLoadingMore: Boolean,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) {
        EmptyState(
            modifier = modifier,
            title = stringResource(Res.string.beneficiary_list_empty_title),
            description = stringResource(Res.string.beneficiary_list_empty_description),
        )
    } else {
        LazyColumn(
            modifier = modifier,
            state = listState,
            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            itemsIndexed(
                items = items,
                key = { _, item -> item.id }
            ) { index, beneficiary ->
                BeneficiaryCard(
                    data = beneficiary,
                    onClick = { onItemClick(beneficiary.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
