package foundation.network.client

import foundation.network.client.plugins.setupClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

object HttpWrapper {
    val client by lazy {
        HttpClient(OkHttp) {
            setupClient()
        }
    }
}
