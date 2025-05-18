package io.github.alaksion.invoicer.foundation.storage

import android.content.Context
import androidx.core.content.edit

internal class AndroidLocalStorage(
    private val context: Context
) : LocalStorage {

    private val preferences by lazy {
        context.getSharedPreferences("invoicer", Context.MODE_PRIVATE)
    }

    override fun setString(value: String, key: String) {
        preferences
            .edit {
                putString(key, value)
            }
    }

    override fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    override fun clear(key: String) {
        preferences.edit { remove(key) }
    }

}
