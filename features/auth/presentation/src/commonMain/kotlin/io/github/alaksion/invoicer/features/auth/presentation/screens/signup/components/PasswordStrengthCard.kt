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
import androidx.compose.ui.text.font.FontWeight
import invoicer.features.auth.presentation.generated.resources.Res
import invoicer.features.auth.presentation.generated.resources.auth_sign_up_password_str_length
import invoicer.features.auth.presentation.generated.resources.auth_sign_up_password_str_lowercase
import invoicer.features.auth.presentation.generated.resources.auth_sign_up_password_str_number
import invoicer.features.auth.presentation.generated.resources.auth_sign_up_password_str_special
import invoicer.features.auth.presentation.generated.resources.auth_sign_up_password_str_title
import invoicer.features.auth.presentation.generated.resources.auth_sign_up_password_str_uppercase
import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthResult
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.HorizontalSpacer
import io.github.alaksion.invoicer.foundation.designSystem.components.spacer.SpacerSize
import io.github.alaksion.invoicer.foundation.designSystem.tokens.AppColor
import io.github.alaksion.invoicer.foundation.designSystem.tokens.AppSize
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import org.jetbrains.compose.resources.stringResource

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
                text = stringResource(Res.string.auth_sign_up_password_str_title),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            FieldStrength(
                text = stringResource(Res.string.auth_sign_up_password_str_length),
                isValid = passwordStrength.lengthValid
            )
            FieldStrength(
                text = stringResource(Res.string.auth_sign_up_password_str_uppercase),
                isValid = passwordStrength.upperCaseValid
            )
            FieldStrength(
                text = stringResource(Res.string.auth_sign_up_password_str_lowercase),
                isValid = passwordStrength.lowerCaseValid
            )
            FieldStrength(
                text = stringResource(Res.string.auth_sign_up_password_str_number),
                isValid = passwordStrength.digitValid
            )
            FieldStrength(
                text = stringResource(Res.string.auth_sign_up_password_str_special),
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
