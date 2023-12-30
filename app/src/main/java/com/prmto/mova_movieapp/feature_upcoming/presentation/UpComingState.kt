package com.prmto.mova_movieapp.feature_upcoming.presentation

data class UpComingState(
    val isLoading: Boolean = false,
    val error: String = "",
    val languageIsoCode: String = "",
)