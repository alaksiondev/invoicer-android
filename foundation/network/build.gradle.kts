import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    id("invoicer.multiplatform.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildKonfig)
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "io.github.alaksion.invoicer.foundation.network"
}

buildkonfig {
    packageName = "io.github.alaksion.invoicer.foundation.network"
    objectName = "NetworkBuildConfig"
    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            value = properties.getProperty("DEBUG_APP_URL"),
            name = "API_URL"
        )
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            // Foundation
            implementation(projects.foundation.session)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.ktor.client.log)
            implementation(libs.ktor.client.auth)

            // KotlinX
            implementation(libs.coroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.ktor.engine.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.engine.darwin)
        }
    }
}