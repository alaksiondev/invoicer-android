package io.github.alaksion.invoicer.features.intermediary.services.data.repository

import io.github.alaksion.invoicer.features.intermediary.services.data.datasource.IntermediaryRemoteDataSource
import io.github.alaksion.invoicer.features.intermediary.services.domain.model.IntermediaryModel
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository

internal class IntermediaryRepositoryImpl(
    private val dataSource: IntermediaryRemoteDataSource
) : IntermediaryRepository {

    override suspend fun createIntermediary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String
    ) {
        dataSource.createIntermediary(
            name = name,
            swift = swift,
            iban = iban,
            bankName = bankName,
            bankAddress = bankAddress,
        )
    }

    override suspend fun getIntermediaries(page: Long, limit: Long): List<IntermediaryModel> {
        val result = dataSource.getIntermediaries(
            limit = limit,
            page = page
        )

        return result.intermediaries.map {
            IntermediaryModel(
                name = it.name,
                iban = it.iban,
                swift = it.swift,
                bankName = it.bankName,
                bankAddress = it.bankAddress,
                id = it.id,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }
    }

    override suspend fun getIntermediaryDetails(id: String): IntermediaryModel {
        return dataSource.getIntermediaryDetails(id).let {
            IntermediaryModel(
                name = it.name,
                iban = it.iban,
                swift = it.swift,
                bankName = it.bankName,
                bankAddress = it.bankAddress,
                id = it.id,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }
    }

    override suspend fun deleteBeneficiary(id: String) {
        dataSource.deleteIntermediary(id)
    }

    override suspend fun updateIntermediary(
        id: String,
        name: String,
        bankName: String,
        bankAddress: String,
        swift: String,
        iban: String
    ) {
        dataSource.updateIntermediary(
            id = id,
            name = name,
            bankName = bankName,
            bankAddress = bankAddress,
            swift = swift,
            iban = iban,
        )
    }
}