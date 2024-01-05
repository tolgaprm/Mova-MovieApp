package com.prmto.explore_data.remote.dto.multisearch

import com.prmto.core_data.util.HandleUtils
import com.prmto.core_data.util.orZero
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.util.MediaType
import com.prmto.explore_domain.model.MultiSearch
import com.prmto.explore_domain.model.PersonSearch
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

fun SearchDto.multiSearch(): MultiSearch? {
    return when (mediaType!!) {
        MediaType.MOVIE.value -> {
            MultiSearch.MovieItem(movie = this.toMovie())
        }

        MediaType.TV_SERIES.value -> {
            MultiSearch.TvSeriesItem(tvSeries = this.toTvSeries())
        }

        MediaType.PERSON.value -> {
            MultiSearch.PersonItem(person = this.toPersonSearch())
        }

        else -> null
    }
}

fun SearchDto.toMovie(): Movie {
    return Movie(
        id = id.orZero(),
        overview = overview.orEmpty(),
        title = title.orEmpty(),
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = HandleUtils.formatVoteCount(voteCount),
        genreIds = genreIds.orEmpty()
    )
}

fun SearchDto.toTvSeries(): TvSeries {
    return TvSeries(
        id = id.orZero(),
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        firstAirDate = HandleUtils.convertToYearFromDate(firstAirDate),
        genreIds = genreIds.orEmpty(),
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = HandleUtils.formatVoteCount(voteCount)
    )
}

fun SearchDto.toPersonSearch(): PersonSearch {
    return PersonSearch(
        id = id.orZero(),
        name = name.orEmpty(),
        profilePath = posterPath,
        knownForDepartment = knownForDepartment.orEmpty(),
        knownFor = knownForDto!!.toKnownForSearch()
    )
}
