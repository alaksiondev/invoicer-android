package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.ui.graphics.vector.ImageVector
import invoicer.features.beneficiary.presentation.generated.resources.Res
import invoicer.features.beneficiary.presentation.generated.resources.beneficiary_details_error_generic_cta
import invoicer.features.beneficiary.presentation.generated.resources.beneficiary_details_error_generic_description
import invoicer.features.beneficiary.presentation.generated.resources.beneficiary_details_error_generic_title
import invoicer.features.beneficiary.presentation.generated.resources.beneficiary_details_error_not_found_cta
import invoicer.features.beneficiary.presentation.generated.resources.beneficiary_details_error_not_found_title
import org.jetbrains.compose.resources.StringResource

internal data class BeneficiaryDetailsState(
    val name: String = "",
    val iban: String = "",
    val swift: String = "",
    val bankName: String = "",
    val bankAddress: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val mode: BeneficiaryDetailsMode = BeneficiaryDetailsMode.Content
)

internal sealed interface BeneficiaryDetailsEvent {
    data class DeleteError(val message: String) : BeneficiaryDetailsEvent
    data object DeleteSuccess : BeneficiaryDetailsEvent
}

internal sealed interface BeneficiaryDetailsMode {
    data object Loading : BeneficiaryDetailsMode
    data object Content : BeneficiaryDetailsMode
    data class Error(val type: BeneficiaryErrorType) : BeneficiaryDetailsMode
}

internal enum class BeneficiaryErrorType(
    val titleResource: StringResource,
    val descriptionResource: StringResource?,
    val ctaResource: StringResource,
    val icon: ImageVector
) {
    NotFound(
        titleResource = Res.string.beneficiary_details_error_not_found_title,
        descriptionResource = null,
        icon = Icons.Outlined.ErrorOutline,
        ctaResource = Res.string.beneficiary_details_error_not_found_cta

    ),
    Generic(
        titleResource = Res.string.beneficiary_details_error_generic_title,
        descriptionResource = Res.string.beneficiary_details_error_generic_description,
        icon = Icons.Outlined.QuestionMark,
        ctaResource = Res.string.beneficiary_details_error_generic_cta
    )
}
