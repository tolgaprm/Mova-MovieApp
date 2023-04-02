package com.prmto.mova_movieapp.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val overview: String,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    var releaseDate: String?,
    val genreIds: List<Int>,
    val voteCount: Int,
    val genresBySeparatedByComma: String = "",
    val voteAverage: Double,
    val genreByOne: String = "",
    val voteCountByString: String = "", // Format like 1000 k
) : Parcelable




