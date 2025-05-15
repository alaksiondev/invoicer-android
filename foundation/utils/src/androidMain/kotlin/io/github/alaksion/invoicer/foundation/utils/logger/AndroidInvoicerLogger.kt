package io.github.alaksion.invoicer.foundation.utils.logger

import android.util.Log

internal class AndroidInvoicerLogger : InvoicerLogger {

    override fun logDebug(message: String, key: String) {
        Log.d(key, message)
    }

    override fun logError(message: String, key: String, throwable: Throwable?) {
        Log.e(key, message, throwable)
    }
}
