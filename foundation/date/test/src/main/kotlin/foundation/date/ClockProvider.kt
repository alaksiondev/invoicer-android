package foundation.date

import foundation.date.impl.DateProvider
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class FakeDateProvider : DateProvider {

    var response: Instant = LocalDateTime(
        year = 2000,
        month = Month.JUNE,
        dayOfMonth = 19,
        hour = 0,
        minute = 0,
        second = 0
    ).toInstant(TimeZone.UTC)

    override fun get(): Instant = response
}