plugins {
    id("invoicer.library")
}

android {
    namespace = "foundation.navigation"
}

dependencies {
    implementation(libs.voyager.navigator)
}