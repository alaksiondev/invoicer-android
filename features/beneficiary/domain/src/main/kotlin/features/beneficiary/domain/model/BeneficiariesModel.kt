package features.beneficiary.domain.model

data class BeneficiariesModel(
    val itemCount: Long,
    val nextPage: Long?,
    val items: List<BeneficiaryModel>,
)
