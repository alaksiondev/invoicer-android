plugins {
    id("invoicer.library")
    id("invoicer.compose")
}

android {
    namespace = "io.github.alaksion.invoicer.features.intermediary.presentation"
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

    // Paging
    implementation(libs.bundles.paging)

    // Foundation
    implementation(projects.foundation.network)
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem)
    implementation(projects.foundation.ui)
    implementation(projects.foundation.exception)
    implementation(projects.foundation.date.impl)

    // Feature
    implementation(projects.features.intermediary.services)
    implementation(projects.foundation.watchers)
}