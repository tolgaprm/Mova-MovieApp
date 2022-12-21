package com.prmto.mova_movieapp.feature_explore.domain.model


data class MovieSearch(
    val id: Int,
    val overview: String,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    var releaseDate: String,
    val genreIds: List<Int>,
    val voteCount: Int,
    val voteAverage: Double,
    val genreByOneForMovie: String = "",
    val voteCountByString: String = ""
)