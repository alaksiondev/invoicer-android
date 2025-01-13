package foundation.logger.test

import foundation.logger.impl.InvoicerLogger

object FakeLogger : InvoicerLogger {
    override fun logDebug(message: String, key: String) = Unit

    override fun logError(message: String, key: String, throwable: Throwable?) = Unit
}