package io.github.alaksion.invoicer.features.intermediary.services.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
internal data class IntermediariesData(
    val intermediaries: List<IntermediaryData>
)

@Serializable
internal data class IntermediaryData(
    val name: String,
    val iban: String,
    val swift: String,
    val bankName: String,
    val bankAddress: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val id: String,
)
