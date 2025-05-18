package io.github.alaksion.invoicer.foundation.storage

interface LocalStorage {
    fun setString(value: String, key: String)
    fun getString(key: String): String?
    fun clear(key: String)
}