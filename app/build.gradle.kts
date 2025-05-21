plugins {
    id("invoicer.application")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    buildFeatures {
        compose = true
    }

    lint {
        disable.add("NullSafeMutableLiveData")
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

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

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
    implementation(projects.foundation.validator)
    implementation(projects.foundation.storage)
    implementation(projects.foundation.auth)
    implementation(projects.foundation.network)
    implementation(projects.foundation.utils)

    // Features
    implementation(projects.features.auth.presentation)
    implementation(projects.features.home)
    implementation(projects.features.invoice)
    implementation(projects.foundation.watchers)
    implementation(projects.features.beneficiary.services)
    implementation(projects.features.beneficiary.presentation)
    implementation(projects.foundation.watchers)
    implementation(projects.foundation.analytics)
    implementation(projects.features.intermediary.services)
    implementation(projects.features.intermediary.presentation)
    implementation(projects.foundation.watchers)
    implementation(projects.features.qrcodeSession)
    implementation(projects.sharedApp)
}