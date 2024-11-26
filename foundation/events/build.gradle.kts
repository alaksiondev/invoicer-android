plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "foundation.events"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.coroutines.core)
}