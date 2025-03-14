package foundation.network

sealed class RequestError : Throwable() {
    data class Http(
        val httpCode: Int,
        val errorCode: Int?,
        override val message: String?
    ) : RequestError()

    data class Other(
        val throwable: Throwable
    ) : RequestError()
}