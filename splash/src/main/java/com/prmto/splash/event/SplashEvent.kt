package com.prmto.splash.event

import com.prmto.navigation.NavigateFlow

sealed class SplashEvent {
    data class NavigateTo(val navigationFlow: NavigateFlow) : SplashEvent()
    data class UpdateAppLanguage(val language: String) : SplashEvent()
    data class UpdateUiMode(val uiMode: Int) : SplashEvent()
}