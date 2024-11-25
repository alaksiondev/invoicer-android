plugins {
    alias(libs.plugins.kotlin.compose)
    id("invoicer.library")
}

android {
    namespace = "foundation.design.system.components"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(projects.foundation.designSystem.tokens)
    api(libs.androidx.material.icons)
}