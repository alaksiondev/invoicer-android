package features.invoice.data.di

import features.invoice.data.datasource.InvoiceDataSource
import features.invoice.data.datasource.InvoiceDataSourceImpl
import features.invoice.data.repository.InvoiceRepositoryImpl
import features.invoice.domain.repository.InvoiceRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val featureInvoiceDataModule = module {
    factory<InvoiceDataSource> {
        InvoiceDataSourceImpl(
            httpClient = get(),
            dispatcher = Dispatchers.IO
        )
    }
    factory<InvoiceRepository> { InvoiceRepositoryImpl(dataSource = get()) }
}