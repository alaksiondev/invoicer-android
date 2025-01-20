package features.invoice.presentation.screens.create.steps.activities.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import features.invoice.presentation.screens.create.steps.activities.AddActivityFormState
import foundation.design.system.tokens.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddActivityBottomSheet(
    sheetState: SheetState,
    formState: AddActivityFormState,
    onDismiss: () -> Unit,
    onChangeDescription: (String) -> Unit,
    onChangeUnitPrice: (String) -> Unit,
    onChangeQuantity: (String) -> Unit,
    onAddActivity: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            val (descriptionFocus, unitPriceFocus, quantityFocus) = FocusRequester.createRefs()
            val keyboard = LocalSoftwareKeyboardController.current

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(descriptionFocus),
                maxLines = 1,
                value = formState.description,
                onValueChange = onChangeDescription,
                keyboardActions = KeyboardActions(
                    onNext = { unitPriceFocus.requestFocus() }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(unitPriceFocus),
                maxLines = 1,
                value = formState.unitPrice,
                onValueChange = onChangeUnitPrice,
                keyboardActions = KeyboardActions(
                    onNext = { quantityFocus.requestFocus() }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal
                )
            )

            OutlinedTextField(
                maxLines = 1,
                value = formState.quantity,
                onValueChange = onChangeQuantity,
                modifier = Modifier
                    .focusRequester(quantityFocus),
                keyboardActions = KeyboardActions(
                    onDone = { keyboard?.hide() }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.NumberPassword
                )
            )

            Button(onAddActivity) { Text(text = "aaa") }
        }
    }
}