package com.prmto.mova_movieapp.core.domain

import androidx.annotation.StringRes
import com.prmto.mova_movieapp.R

data class Language(
    @StringRes val textId: Int,
    val iso: String
)


val supportedLanguages = mutableListOf<Language>(
    Language(R.string.language_english, "en"),
    Language(R.string.language_turkish, "tr"),
    Language(R.string.language_german, "de"),
)
