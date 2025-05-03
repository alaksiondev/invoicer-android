package io.github.alaksion.invoicer.foundation.auth.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import io.github.alaksion.invoicer.foundation.auth.data.datasource.AuthRemoteDataSource
import io.github.alaksion.invoicer.foundation.auth.data.datasource.AuthRemoteDataSourceImpl
import io.github.alaksion.invoicer.foundation.auth.data.datasource.AuthStorage
import io.github.alaksion.invoicer.foundation.auth.data.datasource.AuthStorageImpl
import io.github.alaksion.invoicer.foundation.auth.data.repository.AuthRepositoryImpl
import io.github.alaksion.invoicer.foundation.auth.data.repository.AuthTokenRepositoryImpl
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthTokenRepository
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignInCommandManager
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignInCommandManagerResolver
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignOutHandler
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignOutService
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val foundationAuthDiModule = module {
    factory<AuthStorage> {
        AuthStorageImpl(
            localStorage = get(),
            keyStoreManager = get()
        )
    }
    factory<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(
            httpWrapper = get(),
            dispatcher = Dispatchers.IO
        )
    }
    factory<AuthRepository> {
        AuthRepositoryImpl(
            authStorage = get(),
            remoteDataSource = get()
        )
    }
    factory<AuthTokenRepository> { AuthTokenRepositoryImpl(storage = get()) }

    factory<SignInCommandManager> {
        SignInCommandManagerResolver(
            authRepository = get(),
            authTokenRepository = get(),
            authEventBus = get()
        )
    }

    factory<SignOutService> {
        SignOutHandler(
            authEventBus = get(),
            authRepository = get(),
            firebaseAuth = Firebase.auth
        )
    }
}