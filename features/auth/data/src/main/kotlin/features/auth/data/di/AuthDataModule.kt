package features.auth.data.di

import features.auth.data.datasource.AuthDataSource
import features.auth.data.datasource.AuthDataSourceImpl
import features.auth.data.repository.AuthRepositoryImpl
import features.auth.domain.repository.AuthRepository
import org.koin.dsl.module

val featureAuthDataModule = module {
    factory<AuthDataSource> {
        AuthDataSourceImpl(
            httpClient = get()
        )
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(
            dataSource = get()
        )
    }
}