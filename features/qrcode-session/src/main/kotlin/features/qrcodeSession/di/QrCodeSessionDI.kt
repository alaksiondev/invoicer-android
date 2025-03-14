package features.qrcodeSession.di

import features.qrcodeSession.data.repository.QrCodeTokenRepositoryImpl
import features.qrcodeSession.domain.repository.QrCodeTokenRepository
import features.qrcodeSession.presentation.screens.scan.AuthorizationScanScreenModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val qrCodeSessionDi = module {
    factory<QrCodeTokenRepository> {
        QrCodeTokenRepositoryImpl(
            httpWrapper = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory {
        AuthorizationScanScreenModel(
            uuidValidator = get(),
            qrCodeTokenRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }
}