enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.kotlinx.kover.aggregation") version "0.9.1"
}

kover {
    enableCoverage()

    reports {
        excludesAnnotatedBy = setOf(
            "io.github.alaksion.invoicer.foundation.utils.notations.IgnoreCoverage",
        )
    }
}

rootProject.name = "invoicer"
include(":app")
include(":foundation:design-system")
include(":features:auth:presentation")
include(":foundation:navigation")
include(":foundation:network")
include(":foundation:exception")
include(":foundation:validator:impl")
include(":foundation:validator:test")
include(":foundation:storage:impl")
include(":foundation:storage:test")
include(":foundation:session")
include(":foundation:auth")
include(":features:home")
include(":foundation:ui")
include(":features:invoice")
include(":features:beneficiary:services")
include(":features:beneficiary:presentation")
include(":features:intermediary:services")
include(":features:intermediary:presentation")
include(":features:qrcode-session")
include(":foundation:watchers")
include(":foundation:utils")
include(":foundation:test-util")
include(":foundation:analytics")
