package io.github.alaksion.invoicer.features.beneficiary.services.domain.model

data class UpdateBeneficiaryViewModel(
    val name: String? = null,
    val iban: String? = null,
    val swift: String? = null,
    val bankName: String? = null,
    val bankAddress: String? = null,
)
