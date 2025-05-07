package io.github.alaksion.invoicer.foundation.utils.money

fun Long.moneyFormat(): String {
    return "$${String.format("%.2f", this / 100.0)}"
}