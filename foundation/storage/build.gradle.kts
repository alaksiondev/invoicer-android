plugins {
    id("invoicer.multiplatform.library")
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.storage"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}
