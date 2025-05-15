plugins {
    id("invoicer.multiplatform.library")
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.utils"

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.datetime)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
    }
}