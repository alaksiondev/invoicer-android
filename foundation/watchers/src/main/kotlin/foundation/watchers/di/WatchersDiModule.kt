package foundation.watchers.di

import foundation.watchers.AuthEventBus
import foundation.watchers.AuthEventBusManager
import foundation.watchers.NewInvoicePublisher
import foundation.watchers.RefreshBeneficiaryPublisher
import org.koin.dsl.module

val watchersDiModule = module {
    single { RefreshBeneficiaryPublisher() }
    single { RefreshBeneficiaryPublisher() }
    single { NewInvoicePublisher() }
    single<AuthEventBus> { AuthEventBusManager }
}