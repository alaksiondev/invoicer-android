package io.github.alaksion.invoicer

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import features.auth.presentation.di.featureAuthPresentationDiModule
import features.auth.presentation.navigation.authScreens
import features.beneficiary.data.di.beneficiaryDataModule
import features.beneficiary.presentation.di.beneficiaryPresentationModule
import features.beneficiary.presentation.navigation.beneficiaryScreens
import features.beneficiary.publisher.di.beneficiaryPublisherModule
import features.home.presentation.di.homePresentationDiModule
import features.home.presentation.navigation.homeContainerScreens
import features.intermediary.data.di.intermediaryDataModule
import features.intermediary.presentation.di.intermediaryPresentationModule
import features.intermediary.presentation.navigation.intermediaryScreens
import features.invoice.data.di.invoiceDataModule
import features.invoice.presentation.di.invoicePresentationModule
import features.invoice.presentation.navigation.invoiceScreens
import foundation.auth.data.di.foundationAuthDataModule
import foundation.auth.watchers.di.foundationAuthPresentationDiModule
import foundation.date.impl.di.foundationDateModule
import foundation.logger.impl.di.foundationLoggerModule
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
            beneficiaryScreens()
            intermediaryScreens()
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
                foundationDateModule,
                invoiceDataModule,
                invoicePresentationModule,
                beneficiaryDataModule,
                beneficiaryPresentationModule,
                beneficiaryPublisherModule,
                intermediaryPresentationModule,
                intermediaryDataModule
            )
        }
    }
}