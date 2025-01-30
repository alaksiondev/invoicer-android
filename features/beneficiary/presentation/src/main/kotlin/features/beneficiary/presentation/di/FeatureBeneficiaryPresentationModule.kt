package features.beneficiary.presentation.di

import features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import features.beneficiary.presentation.screen.details.BeneficiaryDetailsScreenModel
import features.beneficiary.presentation.screen.list.BeneficiaryScreenModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val beneficiaryPresentationModule = module {
    factory {
        BeneficiaryScreenModel(
            beneficiaryRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }

    factory {
        CreateBeneficiaryScreenModel(
            beneficiaryRepository = get(),
            dispatcher = Dispatchers.Default,
            refreshBeneficiaryPublisher = get()
        )
    }

    factory {
        BeneficiaryDetailsScreenModel(
            beneficiaryRepository = get(),
            dispatcher = Dispatchers.Default,
            refreshBeneficiaryPublisher = get()
        )
    }
}