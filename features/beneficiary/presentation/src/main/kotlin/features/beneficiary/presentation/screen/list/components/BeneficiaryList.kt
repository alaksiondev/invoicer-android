package features.beneficiary.presentation.screen.list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import features.auth.design.system.components.emptystate.EmptyState
import features.beneficiary.domain.model.BeneficiaryModel
import features.beneficiary.presentation.R
import foundation.design.system.tokens.Spacing
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun BeneficiaryList(
    items: ImmutableList<BeneficiaryModel>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) {
        EmptyState(
            modifier = modifier.padding(Spacing.medium),
            title = stringResource(R.string.beneficiary_list_empty_title),
            description = stringResource(R.string.beneficiary_list_empty_description),
        )
    } else {
        LazyColumn(
            modifier = modifier,
            state = listState,
            contentPadding = PaddingValues(Spacing.medium)
        ) {
            items(
                items = items,
                key = { it.id }
            ) { beneficiary ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.small)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = beneficiary.name
                        )
                    }
                }
            }
        }
    }
}