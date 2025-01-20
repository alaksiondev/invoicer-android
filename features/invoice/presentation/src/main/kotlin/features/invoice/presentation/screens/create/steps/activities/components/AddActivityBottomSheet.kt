package features.invoice.presentation.screens.create.steps.activities.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import features.invoice.presentation.R
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
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            val (descriptionFocus, unitPriceFocus, quantityFocus) = FocusRequester.createRefs()
            val keyboard = LocalSoftwareKeyboardController.current

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
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
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.invoice_create_activity_form_description_placeholder)
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.invoice_create_activity_form_description_label)
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
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
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.invoice_create_activity_form_description_placeholder)
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.invoice_create_activity_form_price_label)
                    )
                },
                prefix = {
                    Text(
                        text = "$"
                    )
                }
            )

            OutlinedTextField(
                maxLines = 1,
                value = formState.quantity,
                onValueChange = onChangeQuantity,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(quantityFocus),
                keyboardActions = KeyboardActions(
                    onDone = { keyboard?.hide() }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.NumberPassword
                ),
                label = {
                    Text(
                        text = stringResource(R.string.invoice_create_activity_form_quantity_label)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.invoice_create_activity_form_quantity_placeholder)
                    )
                },
                supportingText = {
                    Text(
                        text = stringResource(R.string.invoice_create_activity_form_quantity_support)
                    )
                }
            )

            Button(
                onClick = onAddActivity,
                modifier = Modifier.fillMaxWidth(),
                enabled = formState.formButtonEnabled
            ) {
                Text(text = stringResource(R.string.invoice_add_activity_cta))
            }
        }
    }
}