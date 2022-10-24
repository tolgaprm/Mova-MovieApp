package com.prmto.mova_movieapp.util

import com.prmto.mova_movieapp.BuildConfig

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

    const val DEFAULT_LANGUAGE = "en"
    const val DEFAULT_REGION = "US"
    const val STARTING_PAGE = 1

    const val API_KEY = BuildConfig.API_KEY

    const val ITEMS_PER_PAGE = 20

    const val PREFERENCES_NAME = "mova_preferences_name"

    const val LOCALE_KEY = "locale_key"

    val supportedLanguages = listOf(
        "EN",
        "DE",
        "FR",
        "TR"
    )
}