plugins {
    id("invoicer.application")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
}

android {
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.splashscreen)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Voyager
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.koin)
    implementation(libs.voyager.screenmodel)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    // Foundation
    implementation(projects.foundation.navigation)
    implementation(projects.foundation.designSystem)
    implementation(projects.foundation.network)
    implementation(projects.foundation.validator.impl)
    implementation(projects.foundation.storage.impl)
    implementation(projects.foundation.auth.watchers)
    implementation(projects.foundation.auth.data)
    implementation(projects.foundation.auth.domain)
    implementation(projects.foundation.network)
    implementation(projects.foundation.exception)
    implementation(projects.foundation.logger.impl)
    implementation(projects.foundation.date.impl)

    // Features
    implementation(projects.features.auth.presentation)
    implementation(projects.features.home.presentation)
    implementation(projects.features.invoice)
    implementation(projects.foundation.watchers)
    implementation(projects.features.beneficiary.services)
    implementation(projects.features.beneficiary.presentation)
    implementation(projects.foundation.watchers)
    implementation(projects.features.intermediary.data)
    implementation(projects.features.intermediary.domain)
    implementation(projects.features.intermediary.presentation)
    implementation(projects.foundation.watchers)
    implementation(projects.features.qrcodeSession)
}