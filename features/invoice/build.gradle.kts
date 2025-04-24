plugins {
    id("invoicer.library")
    id("invoicer.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.alasion.invoicer.features.invoice"
}

dependencies {
    // Compose
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
    implementation(projects.foundation.network)
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem)
    implementation(projects.foundation.ui)
    implementation(projects.foundation.exception)
    implementation(projects.foundation.date.impl)
    implementation(projects.foundation.watchers)

    // Features
    implementation(projects.features.beneficiary.domain)
    implementation(projects.features.intermediary.domain)

    // Ktor
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.core)

    // AndroidTest
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.koin.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Test
    testImplementation(libs.coroutines.test)
    testImplementation(projects.foundation.date.test)
}