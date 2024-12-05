package io.github.alaksion.invoicer

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import features.auth.data.di.featureAuthDataModule
import features.auth.domain.di.featureAuthDomainModule
import features.auth.presentation.di.featureAuthPresentationDiModule
import features.auth.presentation.navigation.authScreens
import features.home.presentation.navigation.homeContainerScreens
import foundation.auth.impl.di.foundationAuthDiModule
import foundation.network.client.di.networkDiModule
import foundation.storage.impl.di.storageDiModule
import foundation.validator.impl.di.validatorDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InvoicerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenRegistry {
            authScreens()
            homeContainerScreens()
        }

        startKoin {
            androidContext(this@InvoicerApplication)
            modules(
                featureAuthPresentationDiModule,
                featureAuthDataModule,
                featureAuthDomainModule,
                networkDiModule,
                validatorDiModule,
                storageDiModule,
                foundationAuthDiModule
            )
        }

    }
}