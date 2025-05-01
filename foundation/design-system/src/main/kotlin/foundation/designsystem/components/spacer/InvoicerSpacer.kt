package foundation.designsystem.components.spacer

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import foundation.designsystem.tokens.Spacing

@Composable
fun ColumnScope.Spacer(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun RowScope.Spacer(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun VerticalSpacer(
    height: SpacerSize
) {
    Spacer(modifier = Modifier.height(height.value))
}

@Composable
fun HorizontalSpacer(
    width: SpacerSize
) {
    Spacer(modifier = Modifier.width(width.value))
}

enum class SpacerSize(
    internal val value: Dp
) {
    XSmall2(Spacing.xSmall2),
    XSmall(Spacing.xSmall),
    Small(Spacing.small),
    Medium(Spacing.medium),
    Large(Spacing.large),
    XLarge(Spacing.xLarge),
    XLarge3(Spacing.xLarge3)
}
