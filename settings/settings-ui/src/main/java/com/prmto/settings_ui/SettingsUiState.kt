package com.prmto.settings_ui

import androidx.appcompat.app.AppCompatDelegate

data class SettingsUiState(
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = false,
    val uiMode: Int = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
    val languageIsoCode: String = ""
)