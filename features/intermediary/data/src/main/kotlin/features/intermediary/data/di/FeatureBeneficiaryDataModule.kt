package features.intermediary.data.di

import features.intermediary.data.datasource.IntermediaryRemoteDataSource
import features.intermediary.data.datasource.IntermediaryRemoteDataSourceImpl
import features.intermediary.data.repository.IntermediaryRepositoryImpl
import features.intermediary.domain.repository.IntermediaryRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val featureIntermediaryDataModule = module {
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