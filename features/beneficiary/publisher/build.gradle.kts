plugins {
    id("invoicer.library")
}

android {
    namespace = "features.beneficiary.publisher"
}

dependencies {
    implementation(projects.foundation.events)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}