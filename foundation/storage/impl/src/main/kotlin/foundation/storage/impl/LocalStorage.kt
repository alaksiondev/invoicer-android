package foundation.storage.impl

import android.content.Context

interface LocalStorage {
    fun setString(value: String, key: String)
    fun getString(key: String): String?
    fun clear(key: String)
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
            .putString(key, value)
            .apply()
    }

    override fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    override fun clear(key: String) {
        preferences.edit().remove(key).apply()
    }

}
