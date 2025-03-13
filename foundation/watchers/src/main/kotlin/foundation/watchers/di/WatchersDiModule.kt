package foundation.watchers.di

import foundation.watchers.NewInvoicePublisher
import foundation.watchers.RefreshBeneficiaryPublisher
import org.koin.dsl.module

val watchersDiModule = module {
    single { RefreshBeneficiaryPublisher() }
    single { RefreshBeneficiaryPublisher() }
    single { NewInvoicePublisher() }
}