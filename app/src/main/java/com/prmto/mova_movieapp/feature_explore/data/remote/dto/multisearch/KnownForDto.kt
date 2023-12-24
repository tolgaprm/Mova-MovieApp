package com.prmto.mova_movieapp.feature_explore.data.remote.dto.multisearch

import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.feature_explore.domain.model.KnownForSearch
import com.squareup.moshi.Json

data class KnownForDto(
    val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    val id: Int?,
    @Json(name = "media_type") val mediaType: String?,
    val name: String?,
    @Json(name = "origin_country") val originCountry: List<String>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "original_title") val originalTitle: String?,
    val overview: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?
)

fun List<KnownForDto?>.toKnownForSearch(): List<KnownForSearch> {
    return map {
        KnownForSearch(
            id = it?.id.orZero(),
            overview = it?.overview.orEmpty(),
            firstAirDate = it?.firstAirDate.orEmpty(),
            genreIds = it?.genreIds.orEmpty(),
            mediaType = it?.mediaType.orEmpty(),
            name = it?.name.orEmpty(),
            originalName = it?.originalName.orEmpty(),
            originalTitle = it?.originalTitle.orEmpty(),
            posterPath = it?.posterPath,
            releaseDate = it?.releaseDate.orEmpty(),
            title = it?.title.orEmpty(),
            voteAverage = it?.voteAverage.orZero(),
            voteCount = it?.voteCount.orZero()
        )
    }
}