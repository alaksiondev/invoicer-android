package features.auth.presentation.screens.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import features.auth.design.system.components.spacer.SpacerSize
import features.auth.design.system.components.spacer.VerticalSpacer
import features.auth.presentation.R
import foundation.design.system.tokens.Spacing

@Composable
internal fun PasswordStrength(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.xSmall)
        ) {
            Text(text = stringResource(R.string.auth_sign_up_password_str_title))
            VerticalSpacer(height = SpacerSize.XSmall2)
            Text(
                text = stringResource(R.string.auth_sign_up_password_str_length),
                style = style
            )
            Text(
                text = stringResource(R.string.auth_sign_up_password_str_uppercase),
                style = style
            )
            Text(
                text = stringResource(R.string.auth_sign_up_password_str_lowercase),
                style = style
            )
            Text(
                text = stringResource(R.string.auth_sign_up_password_str_number),
                style = style
            )
            Text(
                text = stringResource(R.string.auth_sign_up_password_str_special),
                style = style
            )
        }
    }
}

private val style: TextStyle
    @Composable
    get() {
        return MaterialTheme.typography.bodySmall.copy()
    }