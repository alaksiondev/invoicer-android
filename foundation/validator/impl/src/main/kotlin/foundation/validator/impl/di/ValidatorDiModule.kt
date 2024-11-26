package foundation.validator.impl.di

import foundation.validator.impl.EmailValidator
import foundation.validator.impl.EmailValidatorImpl
import org.koin.dsl.module

val validatorDiModule = module {
    single<EmailValidator> { EmailValidatorImpl }
}