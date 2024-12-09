package io.github.alaksion.invoicer.main

import androidx.lifecycle.ViewModel
import foundation.auth.domain.repository.AuthRepository

internal class MainViewModel(
    private val authStorage: AuthRepository
) : ViewModel() {

}