package io.github.alaksion.invoicer.foundation.network.client

import io.github.alaksion.invoicer.foundation.network.client.plugins.setupClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual val NetworkEngine: HttpClient = HttpClient(Darwin) {
    setupClient()
}