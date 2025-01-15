package features.beneficiary.publisher.di

import features.beneficiary.publisher.NewBeneficiaryPublisher
import org.koin.dsl.module

val beneficiaryPublisherModule = module {
    single { NewBeneficiaryPublisher() }
}