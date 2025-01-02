package features.beneficiary.presentation.di

import features.beneficiary.presentation.screen.list.BeneficiaryListViewModel
import org.koin.dsl.module

val featureBeneficiaryPresentationModule = module {
    factory { BeneficiaryListViewModel() }
}