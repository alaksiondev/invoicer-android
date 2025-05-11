package foundation.storage.test

import foundation.storage.impl.LocalStorage

class FakeLocalStorage : LocalStorage {

    private val _stringCallHistory = mutableMapOf<String, String>()

    val stringCallHistory: Map<String, String>
        get() = _stringCallHistory

    var clearCalls = 0
        private set

    var getStringResponse = mutableMapOf<String, String>()

    override fun setString(value: String, key: String) {
        _stringCallHistory[key] = value
    }

    override fun getString(key: String): String? = getStringResponse[key]

    override fun clear(key: String) {
        clearCalls++
    }
}
