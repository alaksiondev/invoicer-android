package features.qrcodeSession.data.repository

import features.qrcodeSession.data.model.QrCodeTokenDetailsResponse
import features.qrcodeSession.data.model.toDomain
import features.qrcodeSession.domain.model.QrCodeTokenDetailsModel
import features.qrcodeSession.domain.repository.QrCodeTokenRepository
import io.github.alaksion.invoicer.foundation.network.client.HttpWrapper
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class QrCodeTokenRepositoryImpl(
    private val httpWrapper: HttpWrapper,
    private val dispatcher: CoroutineDispatcher
) : QrCodeTokenRepository {

    override suspend fun getQrCodeDetails(token: String): QrCodeTokenDetailsModel {
        return withContext(dispatcher) {
            httpWrapper.client
                .get("/v1/login_code/${token}")
                .body<QrCodeTokenDetailsResponse>()
                .toDomain()
        }
    }

    override suspend fun consumeQrCode(token: String) {
        return withContext(dispatcher) {
            httpWrapper.client
                .post("/v1/login_code/$token/consume")
        }
    }
}
