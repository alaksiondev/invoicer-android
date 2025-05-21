package io.github.alaksion.invoicer.sharedApp.init.di

import io.github.alaksion.features.home.presentation.di.homePresentationDiModule
import io.github.alaksion.invoicer.features.auth.presentation.di.featureAuthPresentationDiModule
import io.github.alaksion.invoicer.features.beneficiary.presentation.di.beneficiaryPresentationModule
import io.github.alaksion.invoicer.features.beneficiary.services.di.beneficiaryServicesDiModule
import io.github.alaksion.invoicer.features.intermediary.presentation.di.intermediaryPresentationModule
import io.github.alaksion.invoicer.features.intermediary.services.di.intermediaryServicesDiModule
import io.github.alaksion.invoicer.features.invoice.di.invoiceDiModule
import io.github.alaksion.invoicer.features.qrcodeSession.di.qrCodeSessionDi
import io.github.alaksion.invoicer.foundation.analytics.di.analyticsDiModule
import io.github.alaksion.invoicer.foundation.auth.di.foundationAuthDiModule
import io.github.alaksion.invoicer.foundation.network.di.networkDiModule
import io.github.alaksion.invoicer.foundation.storage.di.localStorageDiModule
import io.github.alaksion.invoicer.foundation.utils.di.utilsDiModule
import io.github.alaksion.invoicer.foundation.validator.di.validatorDiModule
import io.github.alaksion.invoicer.foundation.watchers.di.watchersDiModule
import org.koin.dsl.module

internal val appModule = module {
    includes(
        featureAuthPresentationDiModule,
        networkDiModule,
        validatorDiModule,
        localStorageDiModule,
        foundationAuthDiModule,
        homePresentationDiModule,
        utilsDiModule,
        invoiceDiModule,
        beneficiaryServicesDiModule,
        beneficiaryPresentationModule,
        intermediaryPresentationModule,
        intermediaryServicesDiModule,
        qrCodeSessionDi,
        watchersDiModule,
        analyticsDiModule,
    )
}