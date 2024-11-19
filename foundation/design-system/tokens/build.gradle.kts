plugins {
    alias(libs.plugins.kotlin.compose)
    id("invoicer.library")
}

android {
    namespace = "foundation.design.system.tokens"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
}