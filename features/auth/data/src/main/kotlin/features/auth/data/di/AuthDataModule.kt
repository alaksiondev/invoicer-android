package features.auth.data.di

import features.auth.data.datasource.AuthDataSource
import features.auth.data.datasource.AuthDataSourceImpl
import org.koin.dsl.module

val featureAuthDataModule = module {
    factory<AuthDataSource> {
        AuthDataSourceImpl(
            httpClient = get()
        )
    }
}