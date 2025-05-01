package foundation.designsystem.components.buttons

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import foundation.designsystem.components.spacer.HorizontalSpacer
import foundation.designsystem.components.spacer.SpacerSize

enum class InvoicerButtonSize(
    internal val height: Dp
) {
    Regular(40.dp),
    Large(48.dp),
}

@Composable
fun PrimaryButton(
    label: String,
    modifier: Modifier = Modifier,
    size: InvoicerButtonSize = InvoicerButtonSize.Large,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    val colors = if (isLoading) {
        ButtonDefaults.buttonColors().copy(
            disabledContentColor = ButtonDefaults.buttonColors().contentColor,
            disabledContainerColor = ButtonDefaults.buttonColors().containerColor,
        )
    } else {
        ButtonDefaults.buttonColors()
    }

    Button(
        onClick = onClick,
        enabled = isEnabled && isLoading.not(),
        shape = MaterialTheme.shapes.small,
        modifier = modifier.height(size.height),
        colors = colors
    ) {
        if (isLoading) {
            ButtonLoadingState()
        } else {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SecondaryButton(
    label: String,
    modifier: Modifier = Modifier,
    size: InvoicerButtonSize = InvoicerButtonSize.Large,
    isEnabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    val colors = if (isLoading) {
        ButtonDefaults.outlinedButtonColors().copy(
            disabledContentColor = ButtonDefaults.outlinedButtonColors().contentColor,
            disabledContainerColor = ButtonDefaults.outlinedButtonColors().containerColor,
        )
    } else {
        ButtonDefaults.outlinedButtonColors()
    }

    OutlinedButton(
        onClick = onClick,
        enabled = isEnabled && isLoading.not(),
        shape = MaterialTheme.shapes.small,
        modifier = modifier.height(size.height),
        colors = colors
    ) {
        if (isLoading) {
            ButtonLoadingState()
        } else {
            leadingIcon?.let { icon ->
                icon()
                HorizontalSpacer(SpacerSize.Small)
            }
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun LoadingDot(
    offset: Float
) {
    Spacer(
        Modifier
            .size(8.dp)
            .offset(y = -offset.dp)
            .background(
                color = LocalContentColor.current,
                shape = CircleShape
            )
    )
}

@Composable
fun animateOffsetWithDelay(
    delay: Int,
    delayUnit: Int,
    maxOffset: Float
): State<Float> {
    val infiniteTransition = rememberInfiniteTransition()
    return infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                0f at delay with LinearEasing
                maxOffset at delay + delayUnit with LinearEasing
                0f at delay + delayUnit * 2
            }
        )
    )
}

@Composable
private fun ButtonLoadingState() {
    val delayUnit = 300
    val maxOffset = 6f

    val offset1 by animateOffsetWithDelay(delay = 0, maxOffset = maxOffset, delayUnit = delayUnit)
    val offset2 by animateOffsetWithDelay(
        delay = delayUnit,
        maxOffset = maxOffset,
        delayUnit = delayUnit
    )
    val offset3 by animateOffsetWithDelay(
        delay = delayUnit * 2,
        maxOffset = maxOffset,
        delayUnit = delayUnit
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = maxOffset.dp)
    ) {
        val spaceSize = 2.dp

        LoadingDot(
            offset = offset1
        )
        Spacer(Modifier.width(spaceSize))
        LoadingDot(offset = offset2)
        Spacer(Modifier.width(spaceSize))
        LoadingDot(offset = offset3)
    }
}

