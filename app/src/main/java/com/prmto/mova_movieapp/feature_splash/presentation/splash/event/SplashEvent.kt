package com.prmto.mova_movieapp.feature_splash.presentation.splash.event

import androidx.navigation.NavDirections
import com.prmto.mova_movieapp.core.presentation.util.UiText

sealed class SplashEvent {
    data class NavigateTo(val directions: NavDirections) : SplashEvent()
    data class UpdateAppLanguage(val language: String) : SplashEvent()
    data class UpdateUiMode(val uiMode: Int) : SplashEvent()
    data class NetworkError(val uiText: UiText) : SplashEvent()
}