plugins {
    id("invoicer.library")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "foundation.design.system.theme"
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
}