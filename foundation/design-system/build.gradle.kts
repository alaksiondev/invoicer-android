plugins {
    id("invoicer.library")
    id("invoicer.compose")
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "foundation.designSystem"
}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    api(libs.androidx.material.icons)
    testImplementation(kotlin("test"))
}