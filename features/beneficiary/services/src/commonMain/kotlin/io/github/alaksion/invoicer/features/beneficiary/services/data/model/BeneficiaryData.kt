package io.github.alaksion.invoicer.features.beneficiary.services.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
internal data class BeneficiaryData(
    val name: String,
    val iban: String,
    val swift: String,
    val bankName: String,
    val bankAddress: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val id: String,
)
