package io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import foundation.designsystem.components.spacer.Spacer
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.Spacing
import io.github.alaksion.invoicer.features.invoice.domain.model.InvoiceListItem
import io.github.alaksion.invoicer.foundation.utils.date.defaultFormat
import io.github.alaksion.invoicer.foundation.utils.money.moneyFormat
import io.github.alasion.invoicer.features.invoice.R

@Composable
internal fun InvoiceListItem(
    item: InvoiceListItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.xSmall)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InvoiceListVerticalTopic(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.invoice_list_item_number),
                    value = item.externalId,
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Default.OpenInNew,
                    contentDescription = null
                )
            }

            InvoiceListVerticalTopic(
                title = stringResource(R.string.invoice_list_item_recipient),
                value = item.recipientCompany,
            )

            InvoiceListHorizontalTopic(
                title = stringResource(R.string.invoice_list_item_due_date),
                value = item.issueDate.defaultFormat()
            )

            InvoiceListHorizontalTopic(
                title = stringResource(R.string.invoice_list_item_issue_date),
                value = item.dueDate.defaultFormat()
            )

            InvoiceListHorizontalTopic(
                title = stringResource(R.string.invoice_list_item_amount),
                value = item.totalAmount.moneyFormat()
            )
        }
    }
}

@Composable
private fun InvoiceListVerticalTopic(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        VerticalSpacer(SpacerSize.XSmall2)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun InvoiceListHorizontalTopic(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(1f)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
