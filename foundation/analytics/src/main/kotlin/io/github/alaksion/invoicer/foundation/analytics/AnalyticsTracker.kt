package io.github.alaksion.invoicer.foundation.analytics

interface AnalyticsTracker {
    suspend fun track(event: AnalyticsEvent)
}