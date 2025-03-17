package features.qrcodeSession.di

import features.qrcodeSession.data.repository.QrCodeTokenRepositoryImpl
import features.qrcodeSession.domain.repository.QrCodeTokenRepository
import features.qrcodeSession.presentation.screens.confirmation.AuthorizationConfirmationScreenModel
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
            dispatcher = Dispatchers.Default
        )
    }

    factory {
        AuthorizationConfirmationScreenModel(
            qrCodeTokenRepository = get(),
            dispatcher = Dispatchers.Default
        )
    }
}