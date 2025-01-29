package features.auth.design.system.components.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import features.auth.design.system.components.spacer.Spacer
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import foundation.design.system.tokens.Spacing

@Composable
fun Feedback(
    modifier: Modifier = Modifier,
    primaryActionText: String,
    onPrimaryAction: () -> Unit,
    icon: ImageVector,
    title: String,
    description: String?,
    secondaryActionText: String? = null,
    onSecondaryAction: (() -> Unit)? = null,
) {
    if (secondaryActionText != null) {
        require(onSecondaryAction != null) {
            "Secondary action must be provided"
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(1f)
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(Spacing.medium)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(128.dp)
            )
        }
        VerticalSpacer(height = SpacerSize.Medium)
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        VerticalSpacer(height = SpacerSize.XSmall)
        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        Spacer(1f)
        Buttons(
            modifier = Modifier.fillMaxWidth(),
            primaryActionText = primaryActionText,
            secondaryActionText = secondaryActionText,
            onPrimaryAction = onPrimaryAction,
            onSecondaryAction = onSecondaryAction
        )
    }
}

@Composable
private fun Buttons(
    modifier: Modifier = Modifier,
    primaryActionText: String,
    onPrimaryAction: () -> Unit,
    secondaryActionText: String? = null,
    onSecondaryAction: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onPrimaryAction
        ) {
            Text(text = primaryActionText)
        }
        if (secondaryActionText != null && onSecondaryAction != null) {
            VerticalSpacer(height = SpacerSize.Medium)
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSecondaryAction
            ) {
                Text(text = secondaryActionText)
            }
        }
    }
}