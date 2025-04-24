package io.github.alaksion.invoicer.features.beneficiary.services.domain.model

data class BeneficiariesModel(
    val itemCount: Long,
    val nextPage: Long?,
    val items: List<BeneficiaryModel>,
)
