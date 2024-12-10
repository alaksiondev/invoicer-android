package features.home.presentation.tabs.settings

import cafe.adriel.voyager.core.model.ScreenModel
import foundation.auth.domain.repository.AuthRepository

internal class SettingsScreenModel(
    private val authRepository: AuthRepository
) : ScreenModel {
}