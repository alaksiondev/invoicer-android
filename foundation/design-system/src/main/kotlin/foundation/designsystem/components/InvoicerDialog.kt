package foundation.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import foundation.designSystem.R
import foundation.designsystem.components.buttons.PrimaryButton
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.AppSize
import foundation.designsystem.tokens.Spacing

@Composable
fun InvoicerDialog(
    onDismiss: () -> Unit,
    variant: DialogVariant,
    title: String,
    description: String
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Column(
                modifier = Modifier.padding(Spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DialogIcon(
                    variant = variant,
                    modifier = Modifier.size(AppSize.xLarge6)
                )
                VerticalSpacer(SpacerSize.Medium)
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                VerticalSpacer(SpacerSize.Medium)
                Text(
                    text = description,
                    textAlign = TextAlign.Center
                )
                VerticalSpacer(SpacerSize.Medium)
                PrimaryButton(
                    label = stringResource(R.string.design_dialog_dismiss_default),
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun DialogIcon(
    variant: DialogVariant,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    val tint = remember(variant) {
        when (variant) {
            DialogVariant.Error -> colors.error
        }
    }

    val vector = remember(variant) {
        when (variant) {
            DialogVariant.Error -> Icons.Outlined.ErrorOutline
        }
    }

    Icon(
        imageVector = vector,
        contentDescription = null,
        tint = tint,
        modifier = modifier
    )
}

enum class DialogVariant {
    Error
}
