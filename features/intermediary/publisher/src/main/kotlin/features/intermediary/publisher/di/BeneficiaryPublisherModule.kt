package features.intermediary.publisher.di

import features.intermediary.publisher.NewIntermediaryPublisher
import org.koin.dsl.module

val intermediaryPublisherModule = module {
    single { NewIntermediaryPublisher() }
}