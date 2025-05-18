plugins {
    id("invoicer.multiplatform.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.alaksion.invoicer.foundation.session"
}