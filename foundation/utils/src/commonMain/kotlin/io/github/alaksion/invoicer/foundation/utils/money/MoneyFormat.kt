package io.github.alaksion.invoicer.foundation.utils.money

import java.util.Locale

private const val MoneyMultiplier = 100.0

fun Long.moneyFormat(
    locale: Locale = Locale.getDefault()
): String {
    return "$${String.format(locale, "%.2f", this / MoneyMultiplier)}"
}
