package features.beneficiary.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import features.beneficiary.presentation.screen.list.BeneficiaryListScreen
import foundation.navigation.InvoicerScreen

val beneficiaryScreenModule = screenModule {
    register<InvoicerScreen.Beneficiary.List> {
        BeneficiaryListScreen()
    }
}