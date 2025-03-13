package di

import features.qrcodeSession.data.repository.QrCodeTokenRepositoryImpl
import features.qrcodeSession.domain.repository.QrCodeTokenRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val qrCodeSessionDi = module {
    factory<QrCodeTokenRepository> {
        QrCodeTokenRepositoryImpl(
            httpWrapper = get(),
            dispatcher = Dispatchers.IO
        )
    }
}