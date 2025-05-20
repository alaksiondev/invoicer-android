package io.github.alaksion.invoicer.features.invoice.presentation.fakes

import io.github.alaksion.invoicer.foundation.utils.date.DateProvider
import kotlinx.datetime.Instant

class FakeDateProvider : DateProvider {

    var instant: Instant = Instant.parse("2023-10-01T12:00:00Z")

    override fun get(): Instant = instant
}