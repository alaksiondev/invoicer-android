package io.github.alaksion.invoicer

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import features.auth.presentation.di.featureAuthPresentationDiModule
import features.auth.presentation.navigation.authScreens
import features.home.presentation.di.homePresentationDiModule
import features.home.presentation.navigation.homeContainerScreens
import features.invoice.data.di.featureInvoiceDataModule
import features.invoice.presentation.di.featureInvoicePresentationModule
import features.invoice.presentation.navigation.invoiceScreens
import foundation.auth.data.di.foundationAuthDataModule
import foundation.auth.watchers.di.foundationAuthPresentationDiModule
import foundation.logger.di.foundationLoggerModule
import foundation.network.client.di.networkDiModule
import foundation.storage.impl.di.storageDiModule
import foundation.validator.impl.di.validatorDiModule
import io.github.alaksion.invoicer.main.mainDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InvoicerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenRegistry {
            authScreens()
            homeContainerScreens()
            invoiceScreens()
        }

        startKoin {
            androidContext(this@InvoicerApplication)
            modules(
                mainDiModule,
                featureAuthPresentationDiModule,
                networkDiModule,
                validatorDiModule,
                storageDiModule,
                foundationAuthPresentationDiModule,
                foundationAuthDataModule,
                homePresentationDiModule,
                foundationLoggerModule,
                featureInvoiceDataModule,
                featureInvoicePresentationModule
            )
        }

    }
}