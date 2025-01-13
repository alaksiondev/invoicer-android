package foundation.date.impl

import kotlinx.datetime.LocalDate

fun LocalDate.defaultFormat(): String = "${this.dayOfMonth}/${this.monthNumber}/${this.year}"