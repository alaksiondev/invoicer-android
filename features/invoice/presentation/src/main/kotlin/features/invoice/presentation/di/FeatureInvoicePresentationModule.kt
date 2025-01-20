package features.invoice.presentation.di

import features.invoice.presentation.screens.create.CreateInvoiceManager
import features.invoice.presentation.screens.create.steps.activities.InvoiceActivitiesScreenModel
import features.invoice.presentation.screens.create.steps.dates.InvoiceDatesScreenModel
import features.invoice.presentation.screens.create.steps.pickbeneficiary.PickBeneficiaryScreenModel
import features.invoice.presentation.screens.create.steps.recipientcompany.RecipientCompanyScreenModel
import features.invoice.presentation.screens.create.steps.sendercompany.SenderCompanyScreenModel
import features.invoice.presentation.screens.invoicelist.state.InvoiceListScreenModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val featureInvoicePresentationModule = module {
    factory<InvoiceListScreenModel> {
        InvoiceListScreenModel(
            invoiceRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }

    factory {
        SenderCompanyScreenModel(
            manager = get(),
            dispatcher = Dispatchers.Default
        )
    }

    factory {
        RecipientCompanyScreenModel(
            manager = get(),
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
        InvoiceActivitiesScreenModel(
            dispatcher = Dispatchers.Default
        )
    }

    single {
        CreateInvoiceManager(
            dateProvider = get()
        )
    }
}