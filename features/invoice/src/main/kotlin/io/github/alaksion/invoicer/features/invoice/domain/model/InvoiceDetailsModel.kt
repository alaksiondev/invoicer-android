package io.github.alaksion.invoicer.features.invoice.domain.model

import kotlinx.datetime.Instant

data class InvoiceDetailsModel(
    val id: String,
    val externalId: String,
    val senderCompany: InvoiceCompanyModel,
    val recipientCompany: InvoiceCompanyModel,
    val issueDate: Instant,
    val dueDate: Instant,
    val beneficiary: InvoiceBeneficiaryModel,
    val intermediary: InvoiceIntermediaryModel?,
    val createdAt: Instant,
    val updatedAt: Instant,
    val activities: List<InvoiceDetailsActivityModel>
)


data class InvoiceCompanyModel(
    val name: String,
)


data class InvoiceBeneficiaryModel(
    val name: String,
)


data class InvoiceIntermediaryModel(
    val name: String,
)


data class InvoiceDetailsActivityModel(
    val id: String,
    val description: String,
    val unitPrice: Long,
    val quantity: Int
)
