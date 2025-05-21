package io.github.alaksion.invoicer.features.qrcodeSession.domain.repository

import io.github.alaksion.invoicer.features.qrcodeSession.domain.model.QrCodeTokenDetailsModel

internal interface QrCodeTokenRepository {
    suspend fun getQrCodeDetails(token: String): QrCodeTokenDetailsModel
    suspend fun consumeQrCode(token: String)
}
