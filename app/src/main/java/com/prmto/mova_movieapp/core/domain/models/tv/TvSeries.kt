package com.prmto.mova_movieapp.core.domain.models.tv

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvSeries(
    val id: Int,
    val overview: String,
    val name: String,
    val posterPath: String?,
    val firstAirDate: String?,
    val genreIds: List<Int>,
    val voteAverage: Double,
    val genreByOne: String = "",
    val formattedVoteCount: String = "", // Format like 1000 k
) : Parcelable