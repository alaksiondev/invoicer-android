package features.beneficiary.presentation.screen.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    onItemClick: (String) -> Unit,
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
        ) {
            itemsIndexed(
                items = items,
                key = { _, item -> item.id }
            ) { index, beneficiary ->
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(beneficiary.id) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Business,
                            contentDescription = null
                        )
                    },
                    headlineContent = {
                        Text(
                            text = beneficiary.name
                        )
                    },
                    trailingContent = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ChevronRight,
                                contentDescription = null
                            )
                        }
                    }
                )
                if (index != items.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}