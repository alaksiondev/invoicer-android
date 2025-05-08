package io.github.alaksion.invoicer.foundation.analytics.impl

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsEvent
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsTracker
import io.github.alaksion.invoicer.foundation.utils.notations.IgnoreCoverage

@IgnoreCoverage
internal class FirebaseAnalyticsTracker(
    private val tracker: FirebaseAnalytics
) : AnalyticsTracker {

    override fun track(event: AnalyticsEvent) {
        tracker.logEvent(event.name) {
            event.params.keys.forEach { key ->
                val value = event.params[key]
                value?.let { safeValue ->
                    param(
                        key = key,
                        value = safeValue
                    )
                }
            }
        }
    }
}