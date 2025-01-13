package features.invoice.presentation.screens.create.steps.dates

import kotlinx.datetime.LocalDate

internal data class InvoiceDatesState(
    val dueDate: LocalDate = LocalDate(1, 1, 1),
    val issueDate: LocalDate = LocalDate(1, 1, 1),
    private val now: LocalDate = LocalDate(1, 1, 1)
) {
    val issueDateValid = issueDate > now
    val dueDateValid = dueDate > issueDate

    val formValid = issueDateValid && dueDateValid
}

internal enum class InvoiceDateEvents {
    Continue
}
