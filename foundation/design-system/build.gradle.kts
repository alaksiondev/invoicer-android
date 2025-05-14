plugins {
    id("invoicer.multiplatform.library")
    id("invoicer.compose")
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.designSystem"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.components.resources)
                api(compose.materialIconsExtended)
            }
        }
        commonTest {
//            implementation(libs.kotlin.test)
        }
    }
}

compose.resources {
    publicResClass = true
}