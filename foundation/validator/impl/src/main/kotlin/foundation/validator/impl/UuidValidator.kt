package foundation.validator.impl

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UuidValidator {
    fun validate(uuid: String): Boolean
}

@OptIn(ExperimentalUuidApi::class)
internal class UuidValidatorImpl : UuidValidator {

    override fun validate(uuid: String): Boolean {
        return runCatching { Uuid.parse(uuid) }.fold(
            onSuccess = { true },
            onFailure = { false }
        )
    }
}
