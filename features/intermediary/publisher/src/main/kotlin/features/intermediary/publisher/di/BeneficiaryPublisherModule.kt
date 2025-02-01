package features.intermediary.publisher.di

import features.intermediary.publisher.RefreshIntermediaryPublisher
import org.koin.dsl.module

val intermediaryPublisherModule = module {
    single { RefreshIntermediaryPublisher() }
}