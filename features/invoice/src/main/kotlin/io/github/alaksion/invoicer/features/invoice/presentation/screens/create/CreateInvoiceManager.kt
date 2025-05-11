package io.github.alaksion.invoicer.features.invoice.presentation.screens.create

import io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.activities.model.CreateInvoiceActivityUiModel
import io.github.alaksion.invoicer.foundation.utils.date.DateProvider
import io.github.alaksion.invoicer.foundation.utils.date.toZeroHour
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlin.time.Duration.Companion.days

internal class CreateInvoiceManager(
    private val dateProvider: DateProvider
) {

    var externalId: String = ""
    var senderCompanyName: String = ""
    var senderCompanyAddress: String = ""

    var recipientCompanyName: String = ""
    var recipientCompanyAddress: String = ""

    var issueDate: Instant = dateProvider
        .get()
        .toZeroHour(TimeZone.currentSystemDefault())

    var dueDate: Instant = dateProvider
        .get()
        .toZeroHour(TimeZone.currentSystemDefault())
        .plus(7.days)

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
        dueDate = dateProvider
            .get()
            .toZeroHour(TimeZone.currentSystemDefault())
        issueDate = dateProvider
            .get()
            .toZeroHour(TimeZone.currentSystemDefault())
            .plus(7.days)
        beneficiaryId = ""
        beneficiaryName = ""
        activities = mutableListOf()
        intermediaryId = null
        intermediaryName = null
        externalId = ""
    }
}
