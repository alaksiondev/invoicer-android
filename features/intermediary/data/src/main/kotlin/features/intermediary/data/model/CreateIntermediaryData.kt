package features.intermediary.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class CreateIntermediaryData(
    val name: String,
    val swift: String,
    val iban: String,
    val bankName: String,
    val bankAddress: String,
)