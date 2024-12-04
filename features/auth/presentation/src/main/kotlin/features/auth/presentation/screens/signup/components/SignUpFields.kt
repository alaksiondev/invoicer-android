package features.auth.presentation.screens.signup.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import features.auth.presentation.R

@Composable
internal fun SignUpEmailField(
    value: String,
    enabled: Boolean,
    isEmailValid: Boolean,
    onChange: (String) -> Unit,
    onImeAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val supportText = if (isEmailValid) {
        null
    } else {
        stringResource(R.string.auth_sign_up_email_error)
    }

    val trailingIcon = remember(isEmailValid) {
        if (isEmailValid) null
        else Icons.Outlined.ErrorOutline
    }


    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = rememberVectorPainter(
                    image = Icons.Outlined.Email
                ),
                contentDescription = null
            )
        },
        label = {
            Text(stringResource(R.string.auth_sign_up_email_label))
        },
        placeholder = {
            Text(stringResource(R.string.auth_sign_up_email_placeholder))
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Email,
            autoCorrect = false
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() }
        ),
        isError = isEmailValid.not(),
        supportingText = supportText.takeIf { it != null }?.let {
            {
                Text(
                    text = it,
                )
            }
        },
        trailingIcon = if (trailingIcon != null) {
            {
                Icon(
                    painter = rememberVectorPainter(
                        image = trailingIcon
                    ),
                    contentDescription = null
                )
            }
        } else null,
        readOnly = enabled.not()
    )
}

@Composable
internal fun SignUpConfirmEmailField(
    value: String,
    emailMatches: Boolean,
    enabled: Boolean,
    onChange: (String) -> Unit,
    onImeAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val trailingIcon = remember(emailMatches) {
        if (emailMatches) null
        else Icons.Outlined.ErrorOutline
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = rememberVectorPainter(
                    image = Icons.Outlined.AlternateEmail
                ),
                contentDescription = null
            )
        },
        trailingIcon = if (trailingIcon != null) {
            {
                Icon(
                    painter = rememberVectorPainter(
                        image = trailingIcon
                    ),
                    contentDescription = null
                )
            }
        } else null,
        label = {
            Text(stringResource(R.string.auth_sign_up_confirm_email_label))
        },
        placeholder = {
            Text(stringResource(R.string.auth_sign_up_confirm_email_placeholder))
        },
        isError = emailMatches.not(),
        supportingText = emailMatches.takeIf { it.not() }?.let {
            {
                Text(
                    text = stringResource(R.string.auth_sign_up_email_match_error),
                )
            }
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Email,
            autoCorrect = false
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() }
        ),
        readOnly = enabled.not()
    )
}


@Composable
internal fun SignUpPasswordField(
    value: String,
    isCensored: Boolean,
    enabled: Boolean,
    onChange: (String) -> Unit,
    toggleCensorship: () -> Unit,
    onImeAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val trailingIcon = remember(isCensored) {
        if (isCensored) {
            Icons.Outlined.Visibility
        } else {
            Icons.Outlined.VisibilityOff
        }
    }

    val transformation = remember(isCensored) {

        if (isCensored) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = rememberVectorPainter(
                    image = Icons.Outlined.Password
                ),
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(
                onClick = toggleCensorship
            ) {
                Icon(
                    painter = rememberVectorPainter(
                        image = trailingIcon
                    ),
                    contentDescription = null
                )
            }
        },
        visualTransformation = transformation,
        label = {
            Text(stringResource(R.string.auth_sign_up_password_label))
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            autoCorrect = false,
            keyboardType = KeyboardType.Password,
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() }
        ),
        readOnly = enabled.not()
    )
}