package com.prmto.upcoming_ui

data class UpComingState(
    val isLoading: Boolean = false,
    val error: String = "",
    val languageIsoCode: String = "",
)