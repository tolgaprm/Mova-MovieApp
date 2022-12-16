package com.prmto.mova_movieapp.feature_home.data.dto

import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import com.squareup.moshi.Json

data class MovieDto(
    val id: Int,
    val adult: Boolean,
    val overview: String,
    val title: String,
    val popularity: Double,
    val video: Boolean,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "vote_average") val voteAverage: Double
)

fun List<MovieDto>.toMovieList(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            overview = it.overview,
            title = it.title,
            originalTitle = it.originalTitle,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            releaseDate = it.releaseDate,
            genreIds = it.genreIds,
            voteCount = it.voteCount,
            voteAverage = it.voteAverage
        )
    }
}