package io.github.alaksion.invoicer.sharedApp.init.modules.common

import cafe.adriel.voyager.core.registry.ScreenRegistry
import io.github.alaksion.features.home.presentation.navigation.homeContainerScreens
import io.github.alaksion.invoicer.features.auth.presentation.navigation.authScreens
import io.github.alaksion.invoicer.features.beneficiary.presentation.navigation.beneficiaryScreens
import io.github.alaksion.invoicer.features.intermediary.presentation.navigation.intermediaryScreens
import io.github.alaksion.invoicer.features.invoice.presentation.navigation.invoiceScreens
import io.github.alaksion.invoicer.features.qrcodeSession.presentation.navigation.qrCodeNavigationModule
import io.github.alaksion.invoicer.sharedApp.init.modules.ModuleInitializer

internal class VoyagerModule : ModuleInitializer {
    override fun onStart() {
        ScreenRegistry {
            authScreens()
            homeContainerScreens()
            invoiceScreens()
            beneficiaryScreens()
            intermediaryScreens()
            qrCodeNavigationModule()
        }
    }
}