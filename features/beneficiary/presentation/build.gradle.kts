plugins {
    id("invoicer.library")
    id("invoicer.compose")
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "io.github.alaksion.invoicer.features.beneficiary.presentation"
}

dependencies {
    // Compose
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.bundles.compose.debug)

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
    implementation(projects.foundation.utils)

    // Feature
    implementation(projects.features.beneficiary.services)
    implementation(projects.foundation.watchers)

    // Test
    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
}