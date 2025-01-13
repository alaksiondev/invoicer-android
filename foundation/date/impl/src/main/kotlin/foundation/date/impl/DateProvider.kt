package foundation.date.impl

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