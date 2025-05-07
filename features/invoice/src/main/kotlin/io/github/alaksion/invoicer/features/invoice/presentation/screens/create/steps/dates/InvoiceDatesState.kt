package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates

import kotlinx.datetime.Instant

internal data class InvoiceDatesState(
    val dueDate: Instant = Instant.DISTANT_FUTURE,
    val issueDate: Instant = Instant.DISTANT_FUTURE,
    val now: Instant = Instant.DISTANT_FUTURE
) {

    val issueDateValid = issueDate >= now
    val dueDateValid = dueDate >= issueDate
    val formValid = dueDateValid && issueDateValid
}

internal enum class InvoiceDateEvents {
    Continue
}