package io.github.alaksion.invoicer.features.beneficiary.presentation.di

import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.create.CreateBeneficiaryScreenModel
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.details.BeneficiaryDetailsScreenModel
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.list.BeneficiaryListScreenModel
import io.github.alaksion.invoicer.features.beneficiary.presentation.screen.update.UpdateBeneficiaryScreenModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val beneficiaryPresentationModule = module {
    factory {
        BeneficiaryListScreenModel(
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

    factory {
        UpdateBeneficiaryScreenModel(
            beneficiaryRepository = get(),
            dispatcher = Dispatchers.Default,
            refreshBeneficiaryPublisher = get()
        )
    }
}
