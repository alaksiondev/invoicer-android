package features.invoice.presentation.screens.create.steps.confirmation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import features.invoice.presentation.R
import features.invoice.presentation.screens.create.steps.activities.components.NewActivityCardTestTags
import foundation.designsystem.tokens.Spacing

@Composable
internal fun ConfirmActivityCard(
    quantity: Int,
    description: String,
    unitPrice: Long,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.xSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.small)
        ) {
            FieldCard(
                content = description,
                title = stringResource(R.string.invoice_create_activity_list_description),
                modifier = Modifier
                    .weight(1f)
                    .testTag(NewActivityCardTestTags.DESCRIPTION),
            )
            FieldCard(
                content = quantity.toString(),
                title = stringResource(R.string.invoice_create_activity_list_quantity),
                alignment = Alignment.CenterHorizontally,
                modifier = Modifier.testTag(NewActivityCardTestTags.QUANTITY)
            )
            FieldCard(
                content = unitPrice.toString(),
                title = stringResource(R.string.invoice_create_activity_list_unitprice),
                alignment = Alignment.CenterHorizontally,
                modifier = Modifier.testTag(NewActivityCardTestTags.UNIT_PRICE)
            )
        }
    }
}

@Composable
private fun FieldCard(
    content: String,
    title: String,
    alignment: Alignment.Horizontal = Alignment.Start,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = alignment,
            modifier = Modifier.padding(Spacing.xSmall)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}