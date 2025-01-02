package features.beneficiary.presentation.di

import features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import features.beneficiary.presentation.screen.list.BeneficiaryScreenModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val featureBeneficiaryPresentationModule = module {
    factory {
        BeneficiaryScreenModel(
            beneficiaryRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }

    factory {
        CreateBeneficiaryScreenModel(
            beneficiaryRepository = get()
        )
    }
}