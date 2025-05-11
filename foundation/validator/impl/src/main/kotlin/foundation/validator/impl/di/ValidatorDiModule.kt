package foundation.validator.impl.di

import foundation.validator.impl.UuidValidator
import foundation.validator.impl.UuidValidatorImpl
import org.koin.dsl.module

val validatorDiModule = module {
    factory<UuidValidator> { UuidValidatorImpl() }
}
