package io.github.alaksion.invoicer

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import features.auth.data.di.featureAuthDataModule
import features.auth.domain.di.featureAuthDomainModule
import features.auth.presentation.di.featureAuthPresentationDiModule
import features.auth.presentation.navigation.authScreens
import foundation.network.di.networkDiModule
import org.koin.core.context.startKoin

class InvoicerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenRegistry {
            authScreens()
        }

        startKoin {
            modules(
                featureAuthPresentationDiModule,
                featureAuthDataModule,
                featureAuthDomainModule,
                networkDiModule
            )
        }

    }
}