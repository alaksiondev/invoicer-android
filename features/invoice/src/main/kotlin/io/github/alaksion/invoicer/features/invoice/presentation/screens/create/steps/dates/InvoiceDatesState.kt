package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates

import io.github.alaksion.invoicer.foundation.utils.date.toLocalDate
import kotlinx.datetime.TimeZone

internal data class InvoiceDatesState(
    val dueDate: Long = 0,
    val issueDate: Long = 0,
    val now: Long = 0
) {
    val parsedDueDate = dueDate.toLocalDate(TimeZone.currentSystemDefault())
    val parsedIssueDate = issueDate.toLocalDate(TimeZone.currentSystemDefault())

    val issueDateValid = issueDate >= now
    val dueDateValid = dueDate > issueDate
    val formValid = dueDateValid && issueDateValid
}

internal enum class InvoiceDateEvents {
    Continue
}