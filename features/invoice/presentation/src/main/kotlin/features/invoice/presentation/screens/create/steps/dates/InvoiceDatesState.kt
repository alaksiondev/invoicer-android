package features.invoice.presentation.screens.create.steps.dates

import foundation.date.impl.toLocalDate
import kotlinx.datetime.LocalDate

internal data class InvoiceDatesState(
    val dueDate: Long = 0,
    val issueDate: Long = 0,
    private val now: LocalDate = LocalDate(1, 1, 1)
) {
    val parsedDueDate = dueDate.toLocalDate()
    val parsedIssueDate = dueDate.toLocalDate()

    val dueDateValid = parsedDueDate > parsedIssueDate
    val issueDateValid = parsedIssueDate > now
    val formValid = dueDateValid && issueDateValid
}

internal enum class InvoiceDateEvents {
    Continue
}