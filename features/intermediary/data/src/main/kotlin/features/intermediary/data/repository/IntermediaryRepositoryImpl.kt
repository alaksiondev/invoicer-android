package features.intermediary.data.repository

import features.intermediary.data.datasource.IntermediaryRemoteDataSource
import features.intermediary.domain.model.IntermediaryModel
import features.intermediary.domain.repository.IntermediaryRepository

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


}