package com.prmto.mova_movieapp.core.util

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
    const val UI_MODE_KEY = "ui_mode_key"

    const val DISCOVER_DATE_QUERY_FOR_TV = "first_air_date"


    const val DETAIL_DEFAULT_ID = 0
    const val TV_SERIES_STATUS_ENDED = "Ended"

    const val TMDB_MOVIE_URL = "https://www.themoviedb.org/movie/"
    const val TMDB_TV_URL = "https://www.themoviedb.org/tv/"

    const val QUERY_APPEND_TO_RESPONSE = "credits,watch/providers"
    const val HOUR_KEY = "hour"
    const val MINUTES_KEY = "minutes"

    const val LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID = "latestRecyclerViewSeeAllSectionText"

}