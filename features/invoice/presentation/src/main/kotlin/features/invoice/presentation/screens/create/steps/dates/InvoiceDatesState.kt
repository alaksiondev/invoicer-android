package features.invoice.presentation.screens.create.steps.dates

import kotlinx.datetime.LocalDate

internal data class InvoiceDatesState(
    val dueDate: LocalDate,
    val issueDate: LocalDate,
    private val now: LocalDate
) {
    val issueDateValid = issueDate > now
    val dueDateValid = dueDate > issueDate

    val formValid = issueDateValid && dueDateValid
}

internal enum class InvoiceDateEvents {
    Continue
}
