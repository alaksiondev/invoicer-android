package foundation.designsystem.components.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.AutoMirrored.Outlined.ArrowBackIos,
    onBackClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onBackClick
    ) {
        Icon(
            painter = rememberVectorPainter(
                image = icon
            ),
            contentDescription = null
        )
    }
}