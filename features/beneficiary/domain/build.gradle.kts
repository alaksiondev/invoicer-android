plugins {
    id("invoicer.library")
}

android {
    namespace = "features.beneficiary.domain"
}

dependencies {
    implementation(libs.datetime)
}