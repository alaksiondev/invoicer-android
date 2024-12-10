package foundation.logger

import android.util.Log

interface InvoicerLogger {
    fun logDebug(message: String, key: String)
    fun logError(message: String, key: String, throwable: Throwable?)
}

internal object InvoicerLoggerImpl : InvoicerLogger {

    override fun logDebug(message: String, key: String) {
        Log.d(key, message)
    }

    override fun logError(message: String, key: String, throwable: Throwable?) {
        Log.e(key, message, throwable)
    }
}

internal object MutedLogger : InvoicerLogger {
    override fun logDebug(message: String, key: String) = Unit

    override fun logError(message: String, key: String, throwable: Throwable?) = Unit
}