package features.auth.presentation.screens.menu

import cafe.adriel.voyager.core.model.ScreenModel
import features.auth.presentation.firebase.FirebaseHelper

internal class AuthMenuScreenModel(
    private val firebaseHelper: FirebaseHelper
) : ScreenModel {

    fun getGoogleClient() = firebaseHelper.getGoogleClient()
}