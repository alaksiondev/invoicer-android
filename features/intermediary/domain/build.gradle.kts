plugins {
    id("invoicer.library")
}

android {
    namespace = "features.intermediary.domain"
}

dependencies {
    implementation(libs.datetime)
}