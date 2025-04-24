package io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.alaksion.invoicer.features.beneficiary.presentation.R

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
    @StringRes val titleResource: Int,
    @StringRes val descriptionResource: Int?,
    @StringRes val ctaResource: Int,
    val icon: ImageVector
) {
    NotFound(
        titleResource = R.string.beneficiary_details_error_not_found_title,
        descriptionResource = null,
        icon = Icons.Outlined.ErrorOutline,
        ctaResource = R.string.beneficiary_details_error_not_found_cta

    ),
    Generic(
        titleResource = R.string.beneficiary_details_error_generic_title,
        descriptionResource = R.string.beneficiary_details_error_generic_description,
        icon = Icons.Outlined.QuestionMark,
        ctaResource = R.string.beneficiary_details_error_generic_cta
    )
}
