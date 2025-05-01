package foundation.designsystem.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.Dialog
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.Spacing

@Composable
fun InvoicerDialog(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Column(modifier = Modifier.padding(Spacing.small)) {
                content()
            }
        }
    }
}

@Composable
fun DefaultInvoicerDialog(
    onDismiss: () -> Unit,
    icon: ImageVector,
    title: String,
    description: String,
    confirmButtonText: String,
    confirmButtonClick: () -> Unit,
    cancelButtonText: String,
) {
    InvoicerDialog(
        onDismiss = onDismiss
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        VerticalSpacer(SpacerSize.Medium)
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        VerticalSpacer(SpacerSize.XSmall)
        Text(
            text = description,
        )
        VerticalSpacer(SpacerSize.XLarge3)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onDismiss
            ) {
                Text(
                    text = cancelButtonText
                )
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = confirmButtonClick
            ) {
                Text(
                    text = confirmButtonText
                )
            }
        }
    }
}