plugins {
    id("invoicer.multiplatform.library")
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.navigation"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.voyager.navigator)
        }
    }
}