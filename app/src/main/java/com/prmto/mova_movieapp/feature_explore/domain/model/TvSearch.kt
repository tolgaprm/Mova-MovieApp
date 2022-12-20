package com.prmto.mova_movieapp.feature_explore.domain.model

data class TvSearch(
    val id: Int,
    val overview: String,
    val name: String,
    val originalName: String,
    val posterPath: String?,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val voteCount: Int,
    val voteAverage: Double
)