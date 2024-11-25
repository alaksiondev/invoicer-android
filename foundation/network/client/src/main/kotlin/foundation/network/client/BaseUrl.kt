package foundation.network.client

import foundation.network.BuildConfig

fun baseUrl(endpoint: String): String {
    return BuildConfig.API_URL + endpoint
}