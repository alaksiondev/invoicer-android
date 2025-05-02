package io.github.alaksion.invoicer

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import features.auth.presentation.di.featureAuthPresentationDiModule
import features.auth.presentation.navigation.authScreens
import features.qrcodeSession.di.qrCodeSessionDi
import features.qrcodeSession.presentation.navigation.qrCodeNavigationModule
import foundation.network.di.networkDiModule
import foundation.storage.impl.di.storageDiModule
import foundation.validator.impl.di.validatorDiModule
import foundation.watchers.di.watchersDiModule
import io.github.alaksion.invoicer.features.beneficiary.presentation.di.beneficiaryPresentationModule
import io.github.alaksion.invoicer.features.beneficiary.presentation.navigation.beneficiaryScreens
import io.github.alaksion.invoicer.features.beneficiary.services.di.beneficiaryServicesDiModule
import io.github.alaksion.invoicer.features.home.presentation.di.homePresentationDiModule
import io.github.alaksion.invoicer.features.home.presentation.navigation.homeContainerScreens
import io.github.alaksion.invoicer.features.intermediary.presentation.di.intermediaryPresentationModule
import io.github.alaksion.invoicer.features.intermediary.presentation.navigation.intermediaryScreens
import io.github.alaksion.invoicer.features.intermediary.services.di.intermediaryServicesDiModule
import io.github.alaksion.invoicer.features.invoice.di.invoiceDiModule
import io.github.alaksion.invoicer.features.invoice.presentation.navigation.invoiceScreens
import io.github.alaksion.invoicer.foundation.auth.di.foundationAuthDiModule
import io.github.alaksion.invoicer.foundation.utils.di.utilsDiModule
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
            qrCodeNavigationModule()
        }

        startKoin {
            androidContext(this@InvoicerApplication)
            modules(
                mainDiModule,
                featureAuthPresentationDiModule,
                networkDiModule,
                validatorDiModule,
                storageDiModule,
                foundationAuthDiModule,
                homePresentationDiModule,
                utilsDiModule,
                invoiceDiModule,
                beneficiaryServicesDiModule,
                beneficiaryPresentationModule,
                intermediaryPresentationModule,
                intermediaryServicesDiModule,
                qrCodeSessionDi,
                watchersDiModule
            )
        }
    }
}