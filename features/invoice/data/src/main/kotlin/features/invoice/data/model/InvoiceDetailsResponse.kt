package features.invoice.data.model

import features.invoice.domain.model.InvoiceBeneficiaryModel
import features.invoice.domain.model.InvoiceCompanyModel
import features.invoice.domain.model.InvoiceDetailsActivityModel
import features.invoice.domain.model.InvoiceDetailsModel
import features.invoice.domain.model.InvoiceIntermediaryModel
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


@Serializable
internal data class InvoiceDetailsResponse(
    val id: String,
    val externalId: String,
    val senderCompany: InvoiceDetailsCompanyResponse,
    val recipientCompany: InvoiceDetailsCompanyResponse,
    val issueDate: Instant,
    val dueDate: Instant,
    val beneficiary: InvoiceDetailsBeneficiaryResponse,
    val intermediary: InvoiceDetailsIntermediaryResponse?,
    val createdAt: Instant,
    val updatedAt: Instant,
    val activities: List<InvoiceDetailsActivityResponse>
)

@Serializable
internal data class InvoiceDetailsCompanyResponse(
    val name: String,
    val address: String
)

@Serializable
internal data class InvoiceDetailsBeneficiaryResponse(
    val name: String,
)

@Serializable
internal data class InvoiceDetailsIntermediaryResponse(
    val name: String,
)

@Serializable
internal data class InvoiceDetailsActivityResponse(
    val id: String,
    val description: String,
    val unitPrice: Long,
    val quantity: Int
)

internal fun InvoiceDetailsResponse.toDomainModel(): InvoiceDetailsModel {
    return InvoiceDetailsModel(
        id = this.id,
        externalId = this.externalId,
        senderCompany = InvoiceCompanyModel(
            name = this.senderCompany.name,
            address = this.senderCompany.address
        ),
        recipientCompany = InvoiceCompanyModel(
            name = this.recipientCompany.name,
            address = this.recipientCompany.address
        ),
        issueDate = this.issueDate,
        dueDate = this.dueDate,
        beneficiary = InvoiceBeneficiaryModel(
            name = this.beneficiary.name,
        ),
        intermediary = this.intermediary?.let {
            InvoiceIntermediaryModel(
                name = it.name,
            )
        },
        activities = this.activities.map {
            InvoiceDetailsActivityModel(
                id = it.id,
                description = it.description,
                unitPrice = it.unitPrice,
                quantity = it.quantity
            )
        },
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}