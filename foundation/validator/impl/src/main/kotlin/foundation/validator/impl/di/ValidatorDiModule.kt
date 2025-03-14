package foundation.validator.impl.di

import foundation.validator.impl.EmailValidator
import foundation.validator.impl.EmailValidatorImpl
import foundation.validator.impl.UuidValidator
import foundation.validator.impl.UuidValidatorImpl
import org.koin.dsl.module

val validatorDiModule = module {
    factory<EmailValidator> { EmailValidatorImpl() }
    factory<UuidValidator> { UuidValidatorImpl() }
}