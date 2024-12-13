package features.auth.presentation.screens.signup.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import features.auth.design.system.components.spacer.HorizontalSpacer
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.auth.presentation.R

@Composable
internal fun SignUpCta(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Column(
        modifier = modifier
    ) {
        VerticalSpacer(height = SpacerSize.Small)
        SignInButton(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSignInClick() }
        )
        VerticalSpacer(height = SpacerSize.Small)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            enabled = enabled
        ) {
            Text(stringResource(R.string.auth_sign_up_submit_button))
        }
    }
}

@Composable
private fun SignInButton(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.auth_sign_up_sign_in_button)
        )
        HorizontalSpacer(width = SpacerSize.Medium)
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Login,
            contentDescription = null
        )
    }
}