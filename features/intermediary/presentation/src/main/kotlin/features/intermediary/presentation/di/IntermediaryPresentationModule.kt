package features.intermediary.presentation.di

import features.intermediary.presentation.screen.create.CreateIntermediaryScreenModel
import features.intermediary.presentation.screen.details.IntermediaryDetailsScreenModel
import features.intermediary.presentation.screen.intermediarylist.IntermediaryListScreenModel
import features.intermediary.presentation.screen.update.UpdateIntermediaryScreenModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val intermediaryPresentationModule = module {
    factory {
        IntermediaryListScreenModel(
            intermediaryRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }

    factory {
        CreateIntermediaryScreenModel(
            intermediaryRepository = get(),
            dispatcher = Dispatchers.Default,
            refreshIntermediaryPublisher = get()
        )
    }

    factory {
        IntermediaryDetailsScreenModel(
            intermediaryRepository = get(),
            dispatcher = Dispatchers.Default,
            refreshIntermediaryPublisher = get()
        )
    }

    factory {
        UpdateIntermediaryScreenModel(
            intermediaryRepository = get(),
            dispatcher = Dispatchers.Default,
            refreshIntermediaryPublisher = get()
        )
    }
}