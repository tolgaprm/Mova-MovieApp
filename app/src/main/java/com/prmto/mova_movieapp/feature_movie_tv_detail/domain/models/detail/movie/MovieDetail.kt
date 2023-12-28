package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.movie

import com.prmto.mova_movieapp.core.domain.models.genre.Genre
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.credit.Credit
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.watchProvider.WatchProviderItem

data class MovieDetail(
    val id: Int,
    val genres: List<Genre>,
    val imdbId: String?,
    val originalTitle: String,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    var convertedRuntime: Map<String, String> = emptyMap(),
    val credit: Credit?,
    var ratingValue: Float = 0f,
    val isFavorite: Boolean = false,
    val isWatchList: Boolean = false,
    val watchProviders: WatchProviderItem?,
    val genresBySeparatedByComma: String = "",
)

fun MovieDetail.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview ?: "",
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        genreIds = genres.map { it.id },
        voteAverage = voteAverage,
        genreByOne = genres.first().name
    )
}