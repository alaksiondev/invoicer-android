package io.github.alaksion.invoicer.features.intermediary.presentation.screen.details

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.alaksion.invoicer.features.intermediary.presentation.R

internal data class IntermediaryDetailsState(
    val name: String = "",
    val iban: String = "",
    val swift: String = "",
    val bankName: String = "",
    val bankAddress: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val mode: IntermediaryDetailsMode = IntermediaryDetailsMode.Content
)

internal sealed interface IntermediaryDetailsEvent {
    data class DeleteError(val message: String) : IntermediaryDetailsEvent
    data object DeleteSuccess : IntermediaryDetailsEvent
}

internal sealed interface IntermediaryDetailsMode {
    data object Loading : IntermediaryDetailsMode
    data object Content : IntermediaryDetailsMode
    data class Error(val type: IntermediaryErrorType) : IntermediaryDetailsMode
}

internal enum class IntermediaryErrorType(
    @StringRes val titleResource: Int,
    @StringRes val descriptionResource: Int?,
    @StringRes val ctaResource: Int,
    val icon: ImageVector
) {
    NotFound(
        titleResource = R.string.intermediary_details_error_not_found_title,
        descriptionResource = null,
        icon = Icons.Outlined.ErrorOutline,
        ctaResource = R.string.intermediary_details_error_not_found_cta

    ),
    Generic(
        titleResource = R.string.intermediary_details_error_generic_title,
        descriptionResource = R.string.intermediary_details_error_generic_description,
        icon = Icons.Outlined.QuestionMark,
        ctaResource = R.string.intermediary_details_error_generic_cta
    )
}
