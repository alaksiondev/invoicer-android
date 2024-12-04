package foundation.storage.impl

import android.content.Context

interface LocalStorage {
    fun setString(value: String, key: String)
    fun getString(key: String): String?
}

internal class LocalStorageImpl(
    private val context: Context
) : LocalStorage {
    val preferences by lazy {
        context.getSharedPreferences("invoicer", Context.MODE_PRIVATE)
    }

    override fun setString(value: String, key: String) {
        preferences
            .edit()
            .putString(value, key)
            .apply()
    }

    override fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

}