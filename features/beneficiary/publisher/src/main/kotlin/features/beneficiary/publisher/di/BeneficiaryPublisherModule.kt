package features.beneficiary.publisher.di

import features.beneficiary.publisher.RefreshBeneficiaryPublisher
import org.koin.dsl.module

val beneficiaryPublisherModule = module {
    single { RefreshBeneficiaryPublisher() }
}