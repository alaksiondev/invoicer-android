plugins {
    id("invoicer.library")
}

android {
    namespace = "features.invoice.domain"
}

dependencies {
    implementation(libs.datetime)
}