package io.github.alaksion.invoicer.features.invoice.presentation.screens.create

import foundation.date.impl.DateProvider
import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
import kotlin.time.Duration.Companion.days

internal class CreateInvoiceManager(
    private val dateProvider: DateProvider
) {

    var externalId: String = ""
    var senderCompanyName: String = ""
    var senderCompanyAddress: String = ""

    var recipientCompanyName: String = ""
    var recipientCompanyAddress: String = ""

    var issueDate = dateProvider.get().toEpochMilliseconds()
    var dueDate = dateProvider.get().plus(7.days).toEpochMilliseconds()

    var beneficiaryId: String = ""
    var beneficiaryName: String = ""

    var intermediaryId: String? = null
    var intermediaryName: String? = null

    var activities = mutableListOf<CreateInvoiceActivityUiModel>()

    fun clear() {
        senderCompanyAddress = ""
        senderCompanyName = ""
        recipientCompanyName = ""
        recipientCompanyAddress = ""
        dueDate = dateProvider.get().toEpochMilliseconds()
        issueDate = dateProvider.get().plus(7.days).toEpochMilliseconds()
        beneficiaryId = ""
        beneficiaryName = ""
        activities = mutableListOf()
        intermediaryId = null
        intermediaryName = null
        externalId = ""
    }
}