package features.invoice.presentation.screens.create.steps.dates

import foundation.date.impl.toLocalDate
import kotlinx.datetime.TimeZone

internal data class InvoiceDatesState(
    val dueDate: Long = 0,
    val issueDate: Long = 0,
    val now: Long = 0
) {
    val parsedDueDate = dueDate.toLocalDate(TimeZone.UTC)
    val parsedIssueDate = issueDate.toLocalDate(TimeZone.UTC)

    val issueDateValid = issueDate >= now
    val dueDateValid = dueDate > issueDate
    val formValid = dueDateValid && issueDateValid
}

internal enum class InvoiceDateEvents {
    Continue
}