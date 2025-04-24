package io.github.alaksion.invoicer.features.intermediary.services.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class UpdateIntermediaryData(
    val name: String,
    val iban: String,
    val swift: String,
    val bankName: String,
    val bankAddress: String,
)
