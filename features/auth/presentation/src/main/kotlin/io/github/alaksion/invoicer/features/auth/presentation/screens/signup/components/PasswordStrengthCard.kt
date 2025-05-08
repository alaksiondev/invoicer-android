package io.github.alaksion.invoicer.features.auth.presentation.screens.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
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
import foundation.designsystem.components.spacer.HorizontalSpacer
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.tokens.AppColor
import foundation.designsystem.tokens.AppSize
import foundation.designsystem.tokens.Spacing
import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthResult
import io.github.alaksion.invoicer.features.auth.presentation.R

@Composable
internal fun PasswordStrengthCard(
    passwordStrength: PasswordStrengthResult,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.xSmall)
        ) {
            Text(
                text = stringResource(R.string.auth_sign_up_password_str_title),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            FieldStrength(
                text = stringResource(R.string.auth_sign_up_password_str_length),
                isValid = passwordStrength.lengthValid
            )
            FieldStrength(
                text = stringResource(R.string.auth_sign_up_password_str_uppercase),
                isValid = passwordStrength.upperCaseValid
            )
            FieldStrength(
                text = stringResource(R.string.auth_sign_up_password_str_lowercase),
                isValid = passwordStrength.lowerCaseValid
            )
            FieldStrength(
                text = stringResource(R.string.auth_sign_up_password_str_number),
                isValid = passwordStrength.digitValid
            )
            FieldStrength(
                text = stringResource(R.string.auth_sign_up_password_str_special),
                isValid = passwordStrength.specialCharacterValid
            )
        }
    }
}

@Composable
private fun FieldStrength(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FieldIcon(
            value = isValid,
            modifier = Modifier.size(AppSize.large)
        )
        HorizontalSpacer(SpacerSize.XSmall2)
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FieldIcon(
    value: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    val icon = remember(value) {
        if (value) Icons.Outlined.Check else Icons.Outlined.Close
    }
    val tint = remember(value) {
        if (value) AppColor.SuccessGreen else colors.error
    }

    Icon(
        modifier = modifier,
        imageVector = icon,
        tint = tint,
        contentDescription = null
    )

}
