package io.github.alaksion.invoicer.features.auth.presentation.fakes

import io.github.alaksion.invoicer.foundation.analytics.AnalyticsEvent
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsTracker

class FakeAnalyticsTracker : AnalyticsTracker {

    var lastEvent: AnalyticsEvent? = null

    override fun track(event: AnalyticsEvent) {
        lastEvent = event
    }
}