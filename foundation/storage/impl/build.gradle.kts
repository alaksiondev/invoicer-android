plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.storate.impl"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}
