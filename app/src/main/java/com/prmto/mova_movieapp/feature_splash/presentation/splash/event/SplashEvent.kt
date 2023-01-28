package com.prmto.mova_movieapp.feature_splash.presentation.splash.event

import androidx.navigation.NavDirections

sealed class SplashEvent {
    data class NavigateTo(val directions: NavDirections) : SplashEvent()
    data class UpdateAppLanguage(val language: String) : SplashEvent()
    data class UpdateUiMode(val uiMode: Int) : SplashEvent()
}