package io.github.alaksion.invoicer.features.beneficiary.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import foundation.navigation.InvoicerScreen
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.CreateBeneficiaryFlow
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list.BeneficiaryListScreen

val beneficiaryScreens = screenModule {
    register<InvoicerScreen.Beneficiary.List> {
        BeneficiaryListScreen()
    }
    register<InvoicerScreen.Beneficiary.Create> {
        CreateBeneficiaryFlow()
    }
}