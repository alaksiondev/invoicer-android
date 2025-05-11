package io.github.alaksion.invoicer.foundation.analytics.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import io.github.alaksion.invoicer.foundation.analytics.AnalyticsTracker
import io.github.alaksion.invoicer.foundation.analytics.impl.FirebaseAnalyticsTracker
import org.koin.dsl.module

val analyticsDiModule = module {
    factory<AnalyticsTracker> { FirebaseAnalyticsTracker(Firebase.analytics) }
}
