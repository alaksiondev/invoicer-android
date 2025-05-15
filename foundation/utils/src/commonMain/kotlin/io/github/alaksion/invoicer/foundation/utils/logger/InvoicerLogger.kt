package io.github.alaksion.invoicer.foundation.utils.logger

interface InvoicerLogger {
    fun logDebug(message: String, key: String)
    fun logError(message: String, key: String, throwable: Throwable?)
}