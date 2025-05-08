package io.github.alaksion.invoicer.features.auth.presentation.screens.login

import io.github.alaksion.invoicer.foundation.analytics.AnalyticsEvent

internal object LoginAnalytics {

    data object GoogleLoginStarted : AnalyticsEvent {
        override val name: String = "login_google_started"
        override val params: Map<String, String> = mapOf()
    }

    data object GoogleLoginSuccess : AnalyticsEvent {
        override val name: String = "login_google_success"
        override val params: Map<String, String> = mapOf()
    }

    data object GoogleLoginFailure : AnalyticsEvent {
        override val name: String = "login_google_success"
        override val params: Map<String, String> = mapOf()
    }

    data object IdentityLoginStarted : AnalyticsEvent {
        override val name: String = "login_identity_started"
        override val params: Map<String, String> = mapOf()
    }

    data object IdentityLoginSuccess : AnalyticsEvent {
        override val name: String = "login_identity_success"
        override val params: Map<String, String> = mapOf()
    }

    data object IdentityLoginFailure : AnalyticsEvent {
        override val name: String = "login_identity_success"
        override val params: Map<String, String> = mapOf()
    }

}