package features.invoice.presentation.screens.create.steps.confirmation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
internal fun ConfirmationCard(
    label: String,
    content: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = label
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        supportingContent = {
            Text(
                text = content
            )
        },
        modifier = modifier
    )
}