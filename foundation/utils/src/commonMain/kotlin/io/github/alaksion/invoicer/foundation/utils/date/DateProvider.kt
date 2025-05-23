package io.github.alaksion.invoicer.foundation.utils.date

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface DateProvider {
    fun get(): Instant
}

internal object DateProviderImpl : DateProvider {
    override fun get(): Instant {
        return Clock.System.now()
    }
}
