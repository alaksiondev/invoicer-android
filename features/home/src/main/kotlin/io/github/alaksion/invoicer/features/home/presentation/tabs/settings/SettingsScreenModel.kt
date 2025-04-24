package io.github.alaksion.invoicer.features.home.presentation.tabs.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import foundation.auth.domain.repository.AuthRepository
import foundation.auth.watchers.AuthEvent
import foundation.auth.watchers.AuthEventPublisher
import kotlinx.coroutines.launch

internal class SettingsScreenModel(
    private val authRepository: AuthRepository,
    private val authEventPublisher: AuthEventPublisher
) : ScreenModel {

    fun signOut() {
        screenModelScope.launch {
            authEventPublisher.publish(event = AuthEvent.SignOff("User sign out"))
            authRepository.signOut()
        }
    }
}