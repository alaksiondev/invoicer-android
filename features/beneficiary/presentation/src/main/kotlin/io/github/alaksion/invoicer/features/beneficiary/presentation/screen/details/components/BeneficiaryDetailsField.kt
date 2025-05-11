package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import foundation.designsystem.tokens.Spacing

@Composable
internal fun BeneficiaryDetailsField(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    ElevatedCard(modifier = modifier) {
        ListItem(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .fillMaxWidth()
                .padding(Spacing.xSmall),
            headlineContent = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            supportingContent = {
                Text(
                    text = value
                )
            },
            leadingContent = { Icon(imageVector = icon, contentDescription = null) }
        )
    }
}
