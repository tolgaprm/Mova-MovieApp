package com.prmto.mova_movieapp.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvSeries(
    val id: Int,
    val overview: String,
    val name: String,
    val originalName: String,
    val posterPath: String?,
    val firstAirDate: String?,
    val genreIds: List<Int>,
    val voteCount: Int,
    val voteAverage: Double,
    val genreByOne: String = "",
    val voteCountByString: String = "", // Format like 1000 k
) : Parcelable


