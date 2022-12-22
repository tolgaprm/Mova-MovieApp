package com.prmto.mova_movieapp.feature_explore.domain.model

import com.prmto.mova_movieapp.feature_explore.domain.util.MediaType


data class PersonSearch(
    val id: Int,
    val name: String,
    val profilePath: String?,
    val knownForDepartment: String?,
    val knownFor: List<KnownForSearch>
)


data class KnownForSearch(
    val id: Int,
    val overview: String?,
    val firstAirDate: String?,
    val genreIds: List<Int>?,
    val mediaType: String,
    val name: String?,
    val originalName: String?,
    val originalTitle: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val voteAverage: Double,
    val voteCount: Int
)

fun KnownForSearch.toSearchMovie(): MovieSearch? {
    if (mediaType == MediaType.MOVIE.value) {
        return MovieSearch(
            id = id,
            overview = overview!!,
            title = title!!,
            originalTitle = originalTitle!!,
            posterPath = posterPath,
            releaseDate = releaseDate!!,
            genreIds = genreIds!!,
            voteCount = voteCount,
            voteAverage = voteAverage
        )
    }
    return null
}

fun KnownForSearch.toTvSearch(): TvSearch? {
    if (mediaType == MediaType.TV_SERIES.value) {
        return TvSearch(
            id = id,
            overview = overview!!,
            name = name!!,
            originalName = originalName!!,
            posterPath = posterPath,
            firstAirDate = firstAirDate!!,
            genreIds = genreIds!!,
            voteCount = voteCount,
            voteAverage = voteAverage
        )
    }
    return null
}
