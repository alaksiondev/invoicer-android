package io.github.alaksion.invoicer.foundation.network.client

import kotlinx.serialization.Serializable

@Serializable
internal data class InvoicerHttpError(
    val message: String,
    val timeStamp: String,
    val errorCode: Int = 0
)
