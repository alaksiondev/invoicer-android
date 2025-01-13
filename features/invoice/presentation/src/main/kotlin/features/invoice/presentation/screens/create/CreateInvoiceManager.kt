package features.invoice.presentation.screens.create

import foundation.date.impl.DateProvider
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

internal class CreateInvoiceManager(
    private val dateProvider: DateProvider
) {
    var senderCompanyName: String = ""
    var senderCompanyAddress: String = ""

    var recipientCompanyName: String = ""
    var recipientCompanyAddress: String = ""

    var dueDate = dateProvider.get().toLocalDateTime(TimeZone.currentSystemDefault()).date

    var issueDate =
        dateProvider.get().plus(7.days).toLocalDateTime(TimeZone.currentSystemDefault()).date

    fun clear() {
        senderCompanyAddress = ""
        senderCompanyName = ""
        recipientCompanyName = ""
        recipientCompanyAddress = ""
        dueDate = dateProvider.get().toLocalDateTime(TimeZone.currentSystemDefault()).date

        issueDate =
            dateProvider.get().plus(7.days).toLocalDateTime(TimeZone.currentSystemDefault()).date
    }
}