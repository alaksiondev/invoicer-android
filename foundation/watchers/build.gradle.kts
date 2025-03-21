plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.watchers"
}

dependencies {
    implementation(projects.foundation.ui)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}