package com.prmto.mova_movieapp.feature_person_detail.data.remote.dto

import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CrewForPerson
import com.squareup.moshi.Json

data class CrewDtoForPerson(
    val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "credit_id") val creditId: String?,
    val department: String?,
    @Json(name = "episode_count") val episodeCount: Int?,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    val id: Int?,
    val job: String?,
    @Json(name = "media_type") val mediaType: String?,
    val name: String?,
    @Json(name = "origin_country") val originCountry: List<String>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?
)

fun CrewDtoForPerson.toCrewForPerson(): CrewForPerson {
    return CrewForPerson(
        id = id.orZero(),
        department = department.orEmpty(),
        firstAirDate = firstAirDate,
        job = job.orEmpty(),
        mediaType = mediaType.orEmpty(),
        name = name,
        originalName = originalName,
        originalTitle = originalTitle,
        overview = overview.orEmpty(),
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage.orZero(),
        voteCount = voteCount.orZero(),
        popularity = popularity.orZero(),
        title = title
    )
}