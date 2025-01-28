plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "features.invoice.presentation"
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    implementation(libs.androidx.activity.compose)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // Voyager
    implementation(libs.bundles.voyager)
    implementation(libs.voyager.transitions)

    // Kotlin
    implementation(libs.immutable.collections)
    implementation(libs.datetime)

    // Foundation
    implementation(projects.foundation.network.request)
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem.tokens)
    implementation(projects.foundation.designSystem.components)
    implementation(projects.foundation.events)
    implementation(projects.foundation.exception)
    implementation(projects.foundation.date.impl)

    // Feature
    implementation(projects.features.invoice.domain)
    implementation(projects.features.invoice.publisher)
    implementation(projects.features.beneficiary.domain)
    implementation(projects.features.beneficiary.publisher)
    implementation(projects.features.intermediary.domain)
    implementation(projects.features.intermediary.publisher)

    // AndroidTest
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.koin.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Test
    testImplementation(libs.coroutines.test)
    implementation(projects.foundation.date.test)
}