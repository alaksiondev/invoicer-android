package foundation.date.impl

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun Long.toLocalDate(): LocalDate {
    val instant = Instant.fromEpochMilliseconds(this)
    return instant
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
}

fun LocalDate.toEpochMilliseconds(): Long {
    val toDateTime = LocalDateTime(
        year = this.year,
        dayOfMonth = this.dayOfMonth,
        monthNumber = this.monthNumber,
        hour = 0,
        minute = 0,
        second = 0
    )
    val instant = toDateTime.toInstant(timeZone = TimeZone.currentSystemDefault())
    return instant.toEpochMilliseconds()
}
