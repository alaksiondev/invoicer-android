package features.beneficiary.data.di

import features.beneficiary.data.datasource.BeneficiaryRemoteDataSource
import features.beneficiary.data.datasource.BeneficiaryRemoteDataSourceImpl
import features.beneficiary.data.repository.BeneficiaryRepositoryImpl
import features.beneficiary.domain.repository.BeneficiaryRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val featureBeneficiaryDataModule = module {
    factory<BeneficiaryRemoteDataSource> {
        BeneficiaryRemoteDataSourceImpl(
            dispatcher = Dispatchers.IO,
            httpWrapper = get()
        )
    }

    factory<BeneficiaryRepository> {
        BeneficiaryRepositoryImpl(
            dataSource = get()
        )
    }
}