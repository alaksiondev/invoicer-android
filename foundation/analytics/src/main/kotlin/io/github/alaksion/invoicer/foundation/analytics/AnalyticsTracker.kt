package io.github.alaksion.invoicer.foundation.analytics

interface AnalyticsTracker {
    fun track(event: AnalyticsEvent)
}
