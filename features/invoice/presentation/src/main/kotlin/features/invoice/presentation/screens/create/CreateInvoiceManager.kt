package features.invoice.presentation.screens.create

import foundation.date.impl.DateProvider
import kotlin.time.Duration.Companion.days

internal class CreateInvoiceManager(
    private val dateProvider: DateProvider
) {
    var senderCompanyName: String = ""
    var senderCompanyAddress: String = ""

    var recipientCompanyName: String = ""
    var recipientCompanyAddress: String = ""

    var issueDate = dateProvider.get().toEpochMilliseconds()
    var dueDate = dateProvider.get().plus(7.days).toEpochMilliseconds()

    fun clear() {
        senderCompanyAddress = ""
        senderCompanyName = ""
        recipientCompanyName = ""
        recipientCompanyAddress = ""
        dueDate = dateProvider.get().toEpochMilliseconds()
        issueDate = dateProvider.get().plus(7.days).toEpochMilliseconds()
    }
}