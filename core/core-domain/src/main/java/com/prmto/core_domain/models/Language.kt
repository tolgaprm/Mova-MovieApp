package com.prmto.core_domain.models

import androidx.annotation.StringRes
import com.prmto.core_domain.R

data class Language(
    @StringRes val textId: Int,
    val iso: String
)


val supportedLanguages = mutableListOf<Language>(
    Language(R.string.language_english, "en"),
    Language(R.string.language_turkish, "tr"),
    Language(R.string.language_german, "de"),
)
