package io.github.alaksion.invoicer.features.beneficiary.services.di

import io.github.alaksion.invoicer.features.beneficiary.services.data.datasource.BeneficiaryRemoteDataSource
import io.github.alaksion.invoicer.features.beneficiary.services.data.datasource.BeneficiaryRemoteDataSourceImpl
import io.github.alaksion.invoicer.features.beneficiary.services.data.repository.BeneficiaryRepositoryImpl
import io.github.alaksion.invoicer.features.beneficiary.services.domain.repository.BeneficiaryRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val beneficiaryServicesDiModule = module {
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
