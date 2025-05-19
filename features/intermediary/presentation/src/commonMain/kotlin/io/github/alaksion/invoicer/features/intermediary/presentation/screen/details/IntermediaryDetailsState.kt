package io.github.alaksion.invoicer.features.intermediary.presentation.screen.details


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.ui.graphics.vector.ImageVector
import invoicer.features.intermediary.presentation.generated.resources.Res
import invoicer.features.intermediary.presentation.generated.resources.intermediary_details_error_generic_cta
import invoicer.features.intermediary.presentation.generated.resources.intermediary_details_error_generic_description
import invoicer.features.intermediary.presentation.generated.resources.intermediary_details_error_generic_title
import invoicer.features.intermediary.presentation.generated.resources.intermediary_details_error_not_found_cta
import invoicer.features.intermediary.presentation.generated.resources.intermediary_details_error_not_found_title
import org.jetbrains.compose.resources.StringResource

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
    val titleResource: StringResource,
    val descriptionResource: StringResource?,
    val ctaResource: StringResource,
    val icon: ImageVector
) {
    NotFound(
        titleResource = Res.string.intermediary_details_error_not_found_title,
        descriptionResource = null,
        icon = Icons.Outlined.ErrorOutline,
        ctaResource = Res.string.intermediary_details_error_not_found_cta

    ),
    Generic(
        titleResource = Res.string.intermediary_details_error_generic_title,
        descriptionResource = Res.string.intermediary_details_error_generic_description,
        icon = Icons.Outlined.QuestionMark,
        ctaResource = Res.string.intermediary_details_error_generic_cta
    )
}
