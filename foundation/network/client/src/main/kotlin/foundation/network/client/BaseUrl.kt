package foundation.network.client

fun baseUrl(endpoint: String): String {
    return BuildConfig.API_URL + endpoint
}