package io.github.alaksion.invoicer.features.intermediary.services.di

import io.github.alaksion.invoicer.features.intermediary.services.data.datasource.IntermediaryRemoteDataSource
import io.github.alaksion.invoicer.features.intermediary.services.data.datasource.IntermediaryRemoteDataSourceImpl
import io.github.alaksion.invoicer.features.intermediary.services.data.repository.IntermediaryRepositoryImpl
import io.github.alaksion.invoicer.features.intermediary.services.domain.repository.IntermediaryRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val intermediaryServicesDiModule = module {
    factory<IntermediaryRemoteDataSource> {
        IntermediaryRemoteDataSourceImpl(
            dispatcher = Dispatchers.IO,
            httpWrapper = get()
        )
    }

    factory<IntermediaryRepository> {
        IntermediaryRepositoryImpl(
            dataSource = get()
        )
    }
}
