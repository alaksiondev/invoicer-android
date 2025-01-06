plugins {
    alias(libs.plugins.kotlin.compose)
    id("invoicer.library")
}

android {
    namespace = "foundation.pagination"
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
}