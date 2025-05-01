package features.auth.presentation.screens.login.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
internal fun LoginPasswordField(
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
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Password,
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() }
        ),
        readOnly = enabled.not(),
        shape = MaterialTheme.shapes.large
    )
}

@Composable
internal fun LoginEmailField(
    value: String,
    enabled: Boolean,
    onChange: (String) -> Unit,
    onImeAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
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
            autoCorrectEnabled = false,
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() }
        ),
        readOnly = enabled.not(),
        shape = MaterialTheme.shapes.large
    )
}