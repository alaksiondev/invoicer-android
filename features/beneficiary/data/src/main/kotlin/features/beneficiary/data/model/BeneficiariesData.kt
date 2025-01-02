package features.beneficiary.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class BeneficiariesData(
    val itemCount: Long,
    val nextPage: Long?,
    val items: List<BeneficiaryData>
)
