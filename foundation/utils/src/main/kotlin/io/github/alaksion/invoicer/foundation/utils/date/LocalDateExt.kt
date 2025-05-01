package io.github.alaksion.invoicer.foundation.utils.date

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toLocalDate(
    timeZone: TimeZone
): LocalDate {
    val instant = Instant.fromEpochMilliseconds(this)
    return instant
        .toLocalDateTime(timeZone)
        .date
}