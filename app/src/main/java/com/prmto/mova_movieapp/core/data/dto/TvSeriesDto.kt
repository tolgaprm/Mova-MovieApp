package com.prmto.mova_movieapp.core.data.dto

import com.squareup.moshi.Json

data class TvSeriesDto(
    val id: Int?,
    val popularity: Double?,
    val overview: String?,
    val name: String?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "origin_country") val originCountry: List<String>?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?,
)