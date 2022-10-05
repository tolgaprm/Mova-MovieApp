package com.prmto.mova_movieapp.domain.models

data class Movie(
    val id: Int,
    val overview: String,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val genreIds: List<Int>,
    val voteCount: Int,
    val voteAverage: Double,
)
