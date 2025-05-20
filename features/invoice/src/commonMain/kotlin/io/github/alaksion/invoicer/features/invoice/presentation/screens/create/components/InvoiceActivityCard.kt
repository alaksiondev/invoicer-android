package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import invoicer.features.invoice.generated.resources.Res
import invoicer.features.invoice.generated.resources.invoice_create_activity_list_quantity
import invoicer.features.invoice.generated.resources.invoice_create_activity_list_total_price
import invoicer.features.invoice.generated.resources.invoice_create_activity_list_unitprice
import io.github.alaksion.invoicer.foundation.designSystem.tokens.AppColor
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import io.github.alaksion.invoicer.foundation.utils.money.moneyFormat
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun InvoiceActivityCard(
    quantity: Int,
    description: String,
    unitPrice: Long,
    modifier: Modifier = Modifier
) {
    val totalPrice = remember { quantity * unitPrice }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.small),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.testTag(NewActivityCardTestTags.DESCRIPTION)
            )
            FieldCard(
                content = quantity.toString(),
                title = stringResource(Res.string.invoice_create_activity_list_quantity),
                modifier = Modifier
                    .testTag(NewActivityCardTestTags.QUANTITY)
                    .fillMaxWidth()
            )
            FieldCard(
                content = unitPrice.moneyFormat(),
                title = stringResource(Res.string.invoice_create_activity_list_unitprice),
                modifier = Modifier
                    .testTag(NewActivityCardTestTags.UNIT_PRICE)
                    .fillMaxWidth(),
                contentColor = AppColor.MoneyGreen,
            )

            FieldCard(
                content = totalPrice.moneyFormat(),
                title = stringResource(Res.string.invoice_create_activity_list_total_price),
                contentColor = AppColor.MoneyGreen,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FieldCard(
    content: String,
    title: String,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor,
            fontWeight = FontWeight.Medium

        )
    }
}

internal object NewActivityCardTestTags {
    const val DESCRIPTION = "new_activity_item_description"
    const val QUANTITY = "new_activity_item_quantity"
    const val UNIT_PRICE = "new_activity_item_unit_price"
    const val DELETE = "new_activity_item_delete"
}
