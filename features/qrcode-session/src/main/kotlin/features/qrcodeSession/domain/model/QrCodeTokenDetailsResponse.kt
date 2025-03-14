package features.qrcodeSession.domain.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

internal data class QrCodeTokenDetailsModel(
    val id: String,
    val agent: String,
    val base64Content: String,
    val rawContent: String,
    val ipAddress: String,
    val status: QrCodeTokenStatusModel,
    val createdAt: Instant,
    val updatedAt: Instant,
    val expiresAt: Instant
)

internal enum class QrCodeTokenStatusModel {
    GENERATED,
    CONSUMED,
    EXPIRED
}