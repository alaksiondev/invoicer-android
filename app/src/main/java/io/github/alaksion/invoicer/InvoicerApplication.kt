package io.github.alaksion.invoicer

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import features.auth.presentation.screens.di.featureAuthDiModule
import features.auth.presentation.screens.navigation.authScreens
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
                featureAuthDiModule,
                networkDiModule
            )
        }

    }
}