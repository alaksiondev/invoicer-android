package features.qrcodeSession.domain.repository

import features.qrcodeSession.domain.model.QrCodeTokenDetailsModel

internal interface QrCodeTokenRepository {
    suspend fun getQrCodeDetails(token: String): QrCodeTokenDetailsModel
    suspend fun consumeQrCode(token: String)
}