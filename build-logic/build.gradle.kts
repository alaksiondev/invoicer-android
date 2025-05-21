plugins {
    `kotlin-dsl`
    `version-catalog`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.library.gradle.plugin)
    implementation(libs.android.application.gradle.plugin)
    implementation(libs.kotlin.multiplatform.gradle.plugin)
    implementation(libs.detekt.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("invoicer-multiplatform-library") {
            id = "invoicer.multiplatform.library"
            implementationClass = "buildLogic.plugins.KmpLibraryPlugin"
        }

        create("invoicer-compose") {
            id = "invoicer.compose"
            implementationClass = "buildLogic.plugins.ComposePlugin"
        }

        create("invoicer-application") {
            id = "invoicer.application"
            implementationClass = "buildLogic.plugins.AppPlugin"
        }
    }
}