package io.github.alaksion.invoicer.foundation.designSystem.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.PrimaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.buttons.SecondaryButton
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.VerticalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.tokens.AppSize
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing

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
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(AppSize.xLarge3)
        )
        VerticalSpacer(SpacerSize.Medium)
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
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
            PrimaryButton(
                label = cancelButtonText,
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            )

            SecondaryButton(
                label = confirmButtonText,
                onClick = confirmButtonClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
