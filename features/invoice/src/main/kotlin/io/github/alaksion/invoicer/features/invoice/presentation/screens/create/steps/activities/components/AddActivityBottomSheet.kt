package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component3
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.AddActivityFormState
import io.github.alaksion.invoicer.foundation.designSystem.components.InputField
import io.github.alaksion.invoicer.foundation.designSystem.tokens.Spacing
import io.github.alasion.invoicer.features.invoice.R

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
        modifier = Modifier.testTag(AddActivityBottomSheetTestTag.CONTENT)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            val (descriptionFocus, unitPriceFocus, quantityFocus) = FocusRequester.createRefs()
            val keyboard = LocalSoftwareKeyboardController.current

            Text(
                text = stringResource(R.string.invoice_activity_new_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            InputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(descriptionFocus)
                    .testTag(AddActivityBottomSheetTestTag.FIELD_DESCRIPTION),
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
                },
            )

            InputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(unitPriceFocus)
                    .testTag(AddActivityBottomSheetTestTag.FIELD_UNIT_PRICE),
                maxLines = 1,
                value = formState.unitPrice,
                onValueChange = { fieldValue ->
                    onChangeUnitPrice(fieldValue.filter { it.isDigit() })
                },
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
                onValueChange = { fieldValue ->
                    onChangeQuantity(fieldValue.filter { it.isDigit() })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(quantityFocus)
                    .testTag(AddActivityBottomSheetTestTag.FIELD_QUANTITY),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(AddActivityBottomSheetTestTag.SUBMIT_BUTTON),
                enabled = formState.formButtonEnabled
            ) {
                Text(text = stringResource(R.string.invoice_add_activity_cta))
            }
        }
    }
}

internal object AddActivityBottomSheetTestTag {
    const val FIELD_DESCRIPTION = "add_activity_description"
    const val FIELD_UNIT_PRICE = "add_activity_unit_price"
    const val FIELD_QUANTITY = "add_activity_quantity"
    const val CONTENT = "add_activity_content"
    const val SUBMIT_BUTTON = "add_activity_submit_buttom"
}
