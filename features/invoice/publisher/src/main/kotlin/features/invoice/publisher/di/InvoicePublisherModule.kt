package features.invoice.publisher.di

import features.invoice.publisher.NewInvoicePublisher
import org.koin.dsl.module

val invoicePublisherModule = module {
    single { NewInvoicePublisher() }
}