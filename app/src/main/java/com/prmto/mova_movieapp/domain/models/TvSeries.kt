package com.prmto.mova_movieapp.domain.models

data class TvSeries(
    val id: Int,
    val overview: String,
    val name: String,
    val originalName: String,
    val posterPath: String?,
    val backdropPath: String?,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val voteCount: Int,
    val voteAverage: Double,
)
