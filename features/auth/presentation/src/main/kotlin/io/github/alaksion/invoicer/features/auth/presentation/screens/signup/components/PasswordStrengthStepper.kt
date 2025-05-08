package io.github.alaksion.invoicer.features.auth.presentation.screens.signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import foundation.designsystem.components.spacer.SpacerSize
import foundation.designsystem.components.spacer.VerticalSpacer
import foundation.designsystem.tokens.AppColor
import foundation.designsystem.tokens.AppSize
import foundation.designsystem.tokens.Spacing
import io.github.alaksion.invoicer.features.auth.presentation.utils.PasswordStrengthResult
import io.github.alaksion.invoicer.features.auth.presentation.R

@Composable
internal fun StrengthStepper(
    passwordStrength: PasswordStrengthResult,
    modifier: Modifier = Modifier
) {
    val strengthMap = remember(passwordStrength) {
        listOf(
            Pair(passwordStrength.lowerCaseValid, R.string.auth_sign_up_password_str_lowercase),
            Pair(passwordStrength.upperCaseValid, R.string.auth_sign_up_password_str_uppercase),
            Pair(passwordStrength.digitValid, R.string.auth_sign_up_password_str_number),
            Pair(
                passwordStrength.specialCharacterValid,
                R.string.auth_sign_up_password_str_special
            ),
            Pair(passwordStrength.lengthValid, R.string.auth_sign_up_password_str_length),
        )
    }

    val strengthLevel = remember(strengthMap) { strengthMap.count { it.first } }

    val validationError = remember(strengthMap) {
        strengthMap.firstOrNull { it.first.not() }
    }

    Column(modifier = modifier) {
        if (validationError != null) {
            ValidationErrorMessage(stringResource(validationError.second))
        } else {
            ValidationSuccessMessage()
        }
        VerticalSpacer(SpacerSize.Small)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xSmall2)
        ) {
            repeat(strengthLevel) {
                StrengthStep(
                    isValid = true,
                    modifier = Modifier.weight(1f)
                )
            }
            repeat(strengthMap.size - strengthLevel) {
                StrengthStep(
                    isValid = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StrengthStep(
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val tint = remember(isValid) {
        if (isValid) AppColor.SuccessGreen else colors.error
    }

    Spacer(
        modifier = modifier
            .height(AppSize.xSmall2)
            .background(tint)
            .clip(MaterialTheme.shapes.extraLarge)
    )
}

@Composable
private fun ValidationErrorMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ).toSpanStyle()
            ) {
                append(stringResource(R.string.auth_sign_up_weak_password_label))
            }
            append(" ")
            withStyle(
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.error
                    ).toSpanStyle()
            ) {
                append(message)
            }
        }
    )
}

@Composable
private fun ValidationSuccessMessage(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ).toSpanStyle()
            ) {
                append(stringResource(R.string.auth_sign_up_strong_password_prefix))
            }
            append(" ")
            withStyle(
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = AppColor.SuccessGreen
                    ).toSpanStyle()
            ) {
                append(stringResource(R.string.auth_sign_up_strong_password_suffix))
            }
        }
    )
}