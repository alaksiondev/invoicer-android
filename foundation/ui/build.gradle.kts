plugins {
    id("invoicer.library")
    id("invoicer.compose")
}

android {
    namespace = "foundation.ui"
}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.coroutines.core)
}