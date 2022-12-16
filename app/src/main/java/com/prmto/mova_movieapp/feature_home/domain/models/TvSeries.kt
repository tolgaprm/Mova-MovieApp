package com.prmto.mova_movieapp.feature_home.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable
