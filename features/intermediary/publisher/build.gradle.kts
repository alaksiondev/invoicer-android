plugins {
    id("invoicer.library")
}

android {
    namespace = "features.intermediary.publisher"
}

dependencies {
    implementation(projects.foundation.ui)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}