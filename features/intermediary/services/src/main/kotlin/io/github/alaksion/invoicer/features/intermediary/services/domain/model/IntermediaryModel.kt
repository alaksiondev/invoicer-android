package io.github.alaksion.invoicer.features.intermediary.services.domain.model

import kotlinx.datetime.Instant

data class IntermediaryModel(
    val name: String,
    val iban: String,
    val swift: String,
    val bankName: String,
    val bankAddress: String,
    val id: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
