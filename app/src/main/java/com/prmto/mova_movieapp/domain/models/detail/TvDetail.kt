package com.prmto.mova_movieapp.domain.models.detail

import com.prmto.mova_movieapp.data.models.Genre
import com.prmto.mova_movieapp.data.models.detail.tv.Season

data class TvDetail(
    val id: Int,
    val genres: List<Genre>,
    val firstAirDate: String,
    val lastAirDate: String,
    val numberOfSeasons: Int,
    val originalName: String,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val seasons: List<Season>,
    val status: String,
    val voteAverage: Double,
    val voteCount: Int
)
