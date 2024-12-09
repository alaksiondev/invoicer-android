package foundation.auth.data.di

import foundation.auth.data.datasource.AuthLocalDataSource
import foundation.auth.data.datasource.AuthLocalDataSourceImpl
import foundation.auth.data.datasource.AuthRemoteDataSource
import foundation.auth.data.datasource.AuthRemoteDataSourceImpl
import foundation.auth.data.repository.AuthRepositoryImpl
import foundation.auth.domain.repository.AuthRepository
import org.koin.dsl.module

val foundationAuthDataModule = module {
    factory<AuthLocalDataSource> { AuthLocalDataSourceImpl(localStorage = get()) }
    factory<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(httpClient = get()) }
    factory<AuthRepository> {
        AuthRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}