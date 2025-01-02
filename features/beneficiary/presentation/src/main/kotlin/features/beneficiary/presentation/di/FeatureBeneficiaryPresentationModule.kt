package features.beneficiary.presentation.di

import features.beneficiary.presentation.screen.list.BeneficiaryScreenModel
import org.koin.dsl.module

val featureBeneficiaryPresentationModule = module {
    factory { BeneficiaryScreenModel() }
}