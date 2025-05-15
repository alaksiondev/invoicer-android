package io.github.alaksion.invoicer.foundation.watchers.di

import io.github.alaksion.invoicer.foundation.watchers.AuthEventBus
import io.github.alaksion.invoicer.foundation.watchers.AuthEventBusManager
import io.github.alaksion.invoicer.foundation.watchers.NewInvoicePublisher
import io.github.alaksion.invoicer.foundation.watchers.RefreshBeneficiaryPublisher
import io.github.alaksion.invoicer.foundation.watchers.RefreshIntermediaryPublisher
import org.koin.dsl.module

val watchersDiModule = module {
    single { RefreshBeneficiaryPublisher() }
    single { RefreshIntermediaryPublisher() }
    single { NewInvoicePublisher() }
    single<AuthEventBus> { AuthEventBusManager }
}
