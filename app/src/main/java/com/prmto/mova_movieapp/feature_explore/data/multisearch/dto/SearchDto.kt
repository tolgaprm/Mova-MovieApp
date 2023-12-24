package com.prmto.mova_movieapp.feature_explore.data.multisearch.dto

import com.prmto.mova_movieapp.core.data.orZero
import com.prmto.mova_movieapp.feature_explore.domain.model.MovieSearch
import com.prmto.mova_movieapp.feature_explore.domain.model.PersonSearch
import com.prmto.mova_movieapp.feature_explore.domain.model.TvSearch
import com.prmto.mova_movieapp.feature_explore.domain.util.MediaType
import com.squareup.moshi.Json

data class SearchDto(
    val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "first_air_date") val firstAirDate: String?,
    val gender: Int?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    val id: Int?,
    @Json(name = "known_for") val knownForDto: List<KnownForDto?>?,
    @Json(name = "known_for_department") val knownForDepartment: String?,
    @Json(name = "media_type") val mediaType: String?,
    val name: String?,
    @Json(name = "origin_country") val originCountry: List<String>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?,
    val genreByOneForMovie: String = "",
    val genreByOneForTv: String = "",
    val voteCountByString: String = ""
)


fun SearchDto.toMovieSearch(): MovieSearch? {
    if (mediaType == MediaType.MOVIE.value) {
        return MovieSearch(
            id = id.orZero(),
            overview = overview.orEmpty(),
            title = title.orEmpty(),
            originalTitle = originalTitle.orEmpty(),
            posterPath = posterPath,
            releaseDate = releaseDate.orEmpty(),
            genreIds = genreIds.orEmpty(),
            voteCount = voteCount.orZero(),
            voteAverage = voteAverage.orZero(),
            genreByOneForMovie = genreByOneForMovie,
            voteCountByString = voteCountByString
        )
    }
    return null
}


fun SearchDto.toTvSearch(): TvSearch? {
    if (mediaType == MediaType.TV_SERIES.value) {
        return TvSearch(
            id = id.orZero(),
            name = name.orEmpty(),
            overview = overview.orEmpty(),
            originalName = originalName.orEmpty(),
            posterPath = posterPath,
            firstAirDate = firstAirDate,
            genreIds = genreIds.orEmpty(),
            voteCount = voteCount.orZero(),
            voteAverage = voteAverage.orZero(),
            genreByOneForTv = genreByOneForTv,
            voteCountByString = voteCountByString
        )
    }
    return null
}

fun SearchDto.toPersonSearch(): PersonSearch? {
    if (mediaType == MediaType.PERSON.value) {
        return PersonSearch(
            id = id.orZero(),
            name = name.orEmpty(),
            profilePath = profilePath,
            knownForDepartment = knownForDepartment,
            knownFor = knownForDto!!.toKnownForSearch()
        )
    }
    return null
}
