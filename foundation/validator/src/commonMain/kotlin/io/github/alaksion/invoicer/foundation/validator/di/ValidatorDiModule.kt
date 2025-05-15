package io.github.alaksion.invoicer.foundation.validator.di

import io.github.alaksion.invoicer.foundation.validator.UuidValidator
import io.github.alaksion.invoicer.foundation.validator.UuidValidatorImpl
import org.koin.dsl.module

val validatorDiModule = module {
    factory<UuidValidator> { UuidValidatorImpl() }
}
