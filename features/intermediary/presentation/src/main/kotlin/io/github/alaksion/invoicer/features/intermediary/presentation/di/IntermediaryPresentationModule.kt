package io.github.alaksion.invoicer.features.intermediary.presentation.di

import io.github.alaksion.invoicer.features.intermediary.presentation.screen.create.CreateIntermediaryScreenModel
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.details.IntermediaryDetailsScreenModel
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.intermediarylist.IntermediaryListScreenModel
import io.github.alaksion.invoicer.features.intermediary.presentation.screen.update.UpdateIntermediaryScreenModel
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