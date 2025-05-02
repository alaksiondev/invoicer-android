package io.github.alaksion.invoicer.features.home.presentation.tabs.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.alaksion.invoicer.foundation.auth.domain.service.SignOutService
import kotlinx.coroutines.launch

internal class SettingsScreenModel(
    private val signOutService: SignOutService,
) : ScreenModel {

    fun signOut() {
        screenModelScope.launch {
            signOutService.signOut()
        }
    }
}