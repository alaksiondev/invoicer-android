package io.github.alaksion.invoicer.foundation.utils.date

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun Instant.toUtc(): Instant {
    return toLocalDateTime(TimeZone.currentSystemDefault())
        .toInstant(TimeZone.UTC)
}

fun Instant.toZeroHour(
    timeZone: TimeZone
): Instant {
    val dateTime = toLocalDateTime(timeZone)
    val zeroHourDateTime = LocalDateTime(
        dateTime.year,
        dateTime.monthNumber,
        dateTime.dayOfMonth,
        0, 0, 0, 0
    )
    return zeroHourDateTime.toInstant(timeZone)
}