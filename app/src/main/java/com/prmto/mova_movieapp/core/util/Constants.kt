package com.prmto.mova_movieapp.core.util

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

    const val DEFAULT_LANGUAGE = "en"
    const val DEFAULT_REGION = "US"
    const val STARTING_PAGE = 1

    const val ITEMS_PER_PAGE = 20

    const val PREFERENCES_NAME = "mova_preferences_name"

    const val LOCALE_KEY = "locale_key"
    const val UI_MODE_KEY = "ui_mode_key"

    const val DISCOVER_DATE_QUERY_FOR_TV = "first_air_date"

    const val QUERY_APPEND_TO_RESPONSE = "credits,watch/providers"

    const val FAVORITE_MOVIE_TABLE_NAME = "favoriteMovieTable"

    const val MOVIE_WATCH_LIST_ITEM_TABLE_NAME = "movieWatchListTable"

    const val FAVORITE_TV_SERIES_TABLE_NAME = "favoriteTvSeriesTable"

    const val TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME = "tvSeriesWatchListTable"

    const val FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME = "FavoriteMovies"
    const val FIREBASE_FAVORITE_TV_DOCUMENT_NAME = "FavoriteTvSeries"
    const val FIREBASE_MOVIE_WATCH_DOCUMENT_NAME = "MovieWatchList"
    const val FIREBASE_TV_WATCH_DOCUMENT_NAME = "TvSeriesWatchList"

    const val FIREBASE_MOVIES_FIELD_NAME = "movies"
    const val FIREBASE_TV_SERIES_FIELD_NAME = "tvSeries"

}