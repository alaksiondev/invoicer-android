package io.github.alaksion.invoicer.foundation.analytics

interface AnalyticsEvent {
    val name: String
    val params: Map<String, String>
}

fun createEvent(
    name: String,
    params: Map<String, String> = mapOf()
): AnalyticsEvent {
    return object : AnalyticsEvent {
        override val name: String = name
        override val params: Map<String, String> = params
    }
}