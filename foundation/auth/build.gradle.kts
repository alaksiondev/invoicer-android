import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    id("invoicer.multiplatform.library")
    id("invoicer.compose")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildKonfig)
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "io.github.alaksion.invoicer.foundation.auth"
}

buildkonfig {
    packageName = "io.github.alaksion.invoicer.foundation.auth"
    objectName = "AuthBuildConfig"
    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            value = properties.getProperty("FIREBASE_WEB_ID"),
            name = "FIREBASE_WEB_ID"
        )
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.foundation)

            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            // Ktor
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.core)

            // Libs
            implementation(projects.foundation.network)
            implementation(projects.foundation.watchers)
            implementation(projects.foundation.storage)
            implementation(projects.foundation.session)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }

        androidMain.dependencies {
            // Firebase
            implementation(libs.androidx.activity.compose)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.auth)
            implementation(libs.bundles.identity)
            implementation(libs.google.services.auth)
            implementation(libs.koin.android)
        }
    }
}

dependencies {
    // Auth Providers
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
}