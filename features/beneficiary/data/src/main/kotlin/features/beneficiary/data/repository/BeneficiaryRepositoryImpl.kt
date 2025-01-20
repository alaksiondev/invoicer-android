package features.beneficiary.data.repository

import features.beneficiary.data.datasource.BeneficiaryRemoteDataSource
import features.beneficiary.domain.model.BeneficiariesModel
import features.beneficiary.domain.model.BeneficiaryModel
import features.beneficiary.domain.repository.BeneficiaryRepository

internal class BeneficiaryRepositoryImpl(
    private val dataSource: BeneficiaryRemoteDataSource
) : BeneficiaryRepository {

    override suspend fun createIntermediary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String
    ) {
        dataSource.createBeneficiary(
            name = name,
            swift = swift,
            iban = iban,
            bankName = bankName,
            bankAddress = bankAddress,
        )
    }

    override suspend fun getBeneficiaries(page: Long, limit: Long): BeneficiariesModel {
        val result = dataSource.getBeneficiaries(
            limit = limit,
            page = page
        )

        return BeneficiariesModel(
            itemCount = result.itemCount,
            nextPage = result.nextPage,
            items = result.items.map {
                BeneficiaryModel(
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
        )
    }
}