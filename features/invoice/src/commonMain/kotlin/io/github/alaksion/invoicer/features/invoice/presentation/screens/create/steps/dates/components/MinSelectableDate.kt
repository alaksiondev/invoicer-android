package io.github.alaksion.invoicer.features.invoice.presentation.screens.create.steps.dates.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates

@OptIn(ExperimentalMaterial3Api::class)
class MinSelectableDate(
    private val minYear: Int,
    private val minEpoch: Long,
) : SelectableDates {

    override fun isSelectableDate(utcTimeMillis: Long) =
        utcTimeMillis >= minEpoch

    override fun isSelectableYear(year: Int) = year >= minYear
}
