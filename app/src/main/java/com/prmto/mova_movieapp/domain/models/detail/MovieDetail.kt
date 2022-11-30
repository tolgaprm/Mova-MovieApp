package com.prmto.mova_movieapp.domain.models.detail

import com.prmto.mova_movieapp.data.models.Genre

data class MovieDetail(
    val id: Int,
    val genres: List<Genre>,
    val imdbId: String?,
    val originalTitle: String,
    val title: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val runtime: Int?,
    val voteAverage: Double,
    val voteCount: Int
)
