package io.github.alaksion.invoicer.features.beneficiary.services.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class CreateBeneficiaryData(
    val name: String,
    val swift: String,
    val iban: String,
    val bankName: String,
    val bankAddress: String,
)