package features.invoice.presentation.screens.invoicelist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import features.auth.design.system.components.itemrow.ItemRow
import features.invoice.domain.model.InvoiceListItem
import foundation.design.system.tokens.Spacing

@Composable
internal fun InvoiceListItem(
    item: InvoiceListItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.small)
        ) {
            ItemRow(
                leading = {
                    Icon(
                        imageVector = Icons.Outlined.QrCode2,
                        contentDescription = null
                    )
                },
                content = {
                    Column {
                        Text(
                            text = item.externalId,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                trailing = {
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null
                    )
                }
            )
        }
    }
}