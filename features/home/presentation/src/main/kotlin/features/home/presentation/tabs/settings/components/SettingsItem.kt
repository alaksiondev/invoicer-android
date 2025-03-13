package features.home.presentation.tabs.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
internal fun SettingsItem(
    content: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(content)
        },
        leadingContent = {
            Icon(
                painter = rememberVectorPainter(icon),
                contentDescription = null
            )
        },
        trailingContent = {
            IconButton(onClick) {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.ChevronRight),
                    contentDescription = null
                )
            }
        }
    )
}