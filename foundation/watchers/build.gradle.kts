plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.watchers"
}

dependencies {
    implementation(projects.foundation.ui)
    implementation(libs.coroutines.core)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}