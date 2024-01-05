package com.prmto.core_domain.models.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val overview: String,
    val title: String,
    val isFavorite: Boolean = false,
    val isWatchList: Boolean = false,
    val posterPath: String?,
    var releaseDate: String?,
    val fullReleaseDate: String? = null,
    val genreIds: List<Int>,
    val genresBySeparatedByComma: String = "",
    val voteAverage: Double,
    val genreByOne: String = "",
    val formattedVoteCount: String = "", // Format like 1000 k
) : Parcelable