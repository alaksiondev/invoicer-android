package features.qrcodeSession.data.model

import features.qrcodeSession.domain.model.QrCodeTokenDetailsModel
import features.qrcodeSession.domain.model.QrCodeTokenStatusModel
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
internal data class QrCodeTokenDetailsResponse(
    val id: String,
    val agent: String,
    val base64Content: String,
    val rawContent: String,
    val status: QrCodeTokenStatusResponse,
    val createdAt: Instant,
    val updatedAt: Instant,
    val expiresAt: Instant,
    val ipAddress: String,
)

@Serializable
internal enum class QrCodeTokenStatusResponse {
    GENERATED,
    CONSUMED,
    EXPIRED
}

internal fun QrCodeTokenDetailsResponse.toDomain(): QrCodeTokenDetailsModel {
    return QrCodeTokenDetailsModel(
        id = id,
        agent = agent,
        base64Content = base64Content,
        rawContent = rawContent,
        status = when (status) {
            QrCodeTokenStatusResponse.GENERATED -> QrCodeTokenStatusModel.GENERATED
            QrCodeTokenStatusResponse.CONSUMED -> QrCodeTokenStatusModel.CONSUMED
            QrCodeTokenStatusResponse.EXPIRED -> QrCodeTokenStatusModel.EXPIRED
        },
        createdAt = createdAt,
        updatedAt = updatedAt,
        expiresAt = expiresAt,
        ipAddress = ipAddress
    )
}