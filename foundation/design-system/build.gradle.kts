plugins {
    id("invoicer.library")
    id("invoicer.compose")
}

android {
    namespace = "foundation.designSystem"
}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    api(libs.androidx.material.icons)
}