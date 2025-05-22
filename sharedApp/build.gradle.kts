import org.gradle.kotlin.dsl.commonMainApi

plugins {
    id("invoicer.multiplatform.library")
    id("invoicer.compose")
}

android {
    namespace = "io.github.alaksion.invoicer.sharedApp"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.animationGraphics)
            implementation(compose.foundation)

            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            // Foundation
            implementation(projects.foundation.navigation)
            implementation(projects.foundation.designSystem)
            implementation(projects.foundation.network)
            implementation(projects.foundation.validator)
            api(projects.foundation.storage)
            api(projects.foundation.auth)
            implementation(projects.foundation.network)
            implementation(projects.foundation.utils)

            // Features
            implementation(projects.features.auth.presentation)
            implementation(projects.features.home)
            implementation(projects.features.invoice)
            implementation(projects.foundation.watchers)
            implementation(projects.features.beneficiary.services)
            implementation(projects.features.beneficiary.presentation)
            implementation(projects.foundation.watchers)
            api(projects.foundation.analytics)
            implementation(projects.features.intermediary.services)
            implementation(projects.features.intermediary.presentation)
            implementation(projects.foundation.watchers)
            implementation(projects.features.qrcodeSession)

            // Voyager
            implementation(libs.bundles.voyager)
            implementation(libs.voyager.transitions)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "invoicerShared"
            isStatic = true
            export(projects.foundation.storage)
            export(projects.foundation.auth)
            export(projects.foundation.analytics)
        }
    }
}
