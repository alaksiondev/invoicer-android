package io.github.alaksion.invoicer.features.invoice.di

import features.invoice.data.repository.InvoiceRepositoryImpl
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickintermediary.PickIntermediaryScreenModel
import io.github.alaksion.invoicer.features.invoice.data.datasource.InvoiceDataSource
import io.github.alaksion.invoicer.features.invoice.data.datasource.InvoiceDataSourceImpl
import io.github.alaksion.invoicer.features.invoice.domain.repository.InvoiceRepository
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.CreateInvoiceManager
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.InvoiceActivitiesScreenModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.company.InvoiceCompanyScreenModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.confirmation.InvoiceConfirmationScreenModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates.InvoiceDatesScreenModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.externalId.InvoiceExternalIdScreenModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.pickbeneficiary.PickBeneficiaryScreenModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.details.InvoiceDetailsScreenModel
import io.github.alaksion.invoicer.features.invoice.presentation.screens.invoicelist.state.InvoiceListScreenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

val invoiceDiModule = module {
    presentation()
    data()
}

private fun Module.presentation() {
    factory<InvoiceListScreenModel> {
        InvoiceListScreenModel(
            invoiceRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }


    factory {
        InvoiceDatesScreenModel(
            dispatcher = Dispatchers.Default,
            manager = get(),
            dateProvider = get()
        )
    }

    factory {
        PickBeneficiaryScreenModel(
            createInvoiceManager = get(),
            dispatcher = Dispatchers.Default,
            beneficiaryRepository = get()
        )
    }

    factory {
        PickIntermediaryScreenModel(
            createInvoiceManager = get(),
            dispatcher = Dispatchers.Default,
            intermediaryRepository = get()
        )
    }

    factory {
        InvoiceActivitiesScreenModel(
            dispatcher = Dispatchers.Default,
            createInvoiceManager = get()
        )
    }

    factory {
        InvoiceConfirmationScreenModel(
            manager = get(),
            repository = get(),
            dispatcher = Dispatchers.Default,
            newInvoicePublisher = get()
        )
    }

    factory {
        InvoiceExternalIdScreenModel(
            dispatcher = Dispatchers.Default,
            manager = get()
        )
    }

    factory {
        InvoiceDetailsScreenModel(
            invoiceRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }

    factory {
        InvoiceCompanyScreenModel(
            manager = get(),
            dispatcher = Dispatchers.Default
        )
    }

    single {
        CreateInvoiceManager(
            dateProvider = get()
        )
    }
}

private fun Module.data() {
    factory<InvoiceDataSource> {
        InvoiceDataSourceImpl(
            httpWrapper = get(),
            dispatcher = Dispatchers.IO
        )
    }
    factory<InvoiceRepository> { InvoiceRepositoryImpl(dataSource = get()) }
}
