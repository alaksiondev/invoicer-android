plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.date.impl"
}

dependencies {
    implementation(libs.datetime)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}