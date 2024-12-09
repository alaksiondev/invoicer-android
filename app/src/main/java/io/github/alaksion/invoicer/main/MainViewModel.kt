package io.github.alaksion.invoicer.main

import androidx.lifecycle.ViewModel
import foundation.auth.impl.storage.AuthStorage

internal class MainViewModel(
    private val authStorage: AuthStorage
) : ViewModel()