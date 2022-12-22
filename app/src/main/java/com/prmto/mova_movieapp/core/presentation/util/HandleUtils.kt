package com.prmto.mova_movieapp.core.presentation.util

import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_home.domain.models.Movie

object HandleUtils {

    fun convertToYearFromDate(releaseDate: String): String {
        return releaseDate.split("-")[0]
    }

    fun convertGenreListToStringSeparatedByCommas(
        movieGenreList: List<Genre>,
        movie: Movie
    ): String {
        var genreNames = ""

        if (movie.genreIds.isEmpty()) {
            return ""
        }

        movieGenreList.forEach { genre ->
            movie.genreIds.forEach {
                if (it == genre.id) {
                    genreNames += "${genre.name}, "
                }
            }
        }

        if (genreNames.isNotEmpty()) {
            return genreNames.subSequence(0, genreNames.length - 2).toString()
        }

        return genreNames
    }

    fun convertGenreListToStringSeparatedByCommas(genreList: List<Genre>): String {
        var genreNames = ""
        if (genreList.isEmpty()) {
            return ""
        }

        genreList.forEach { genre ->
            genreNames += "${genre.name}, "
        }

        if (genreNames.isNotEmpty()) {
            return genreNames.subSequence(0, genreNames.length - 2).toString()
        }

        return genreNames
    }

    fun handleConvertingGenreListToOneGenreString(
        genreList: List<Genre>,
        genreIds: List<Int>
    ): String {
        for (genre: Genre in genreList) {
            for (genreId: Int in genreIds) {
                if (genreId == genre.id) {
                    return genre.name
                }
                break

            }
        }

        return ""

    }

    fun calculateRatingBarValue(voteAverage: Double): Float {
        return ((voteAverage * 5) / 10).toFloat()
    }

    fun convertRuntimeAsHourAndMinutes(runtime: Int?): Map<String, String> {
        runtime?.let {
            val hour = runtime / 60
            val minutes = (runtime % 60)
            return mapOf(
                Constants.HOUR_KEY to hour.toString(),
                Constants.MINUTES_KEY to minutes.toString()
            )
        } ?: return emptyMap()
    }

    fun convertTvSeriesReleaseDateBetweenFirstAndLastDate(
        firstAirDate: String,
        lastAirDate: String,
        status: String
    ): String {
        val firstAirDateValue = convertToYearFromDate(firstAirDate)
        return if (status == Constants.TV_SERIES_STATUS_ENDED) {
            val lastAirDateValue = convertToYearFromDate(lastAirDate)
            "${firstAirDateValue}-${lastAirDateValue}"
        } else {
            "$firstAirDateValue-"
        }
    }

    fun convertingVoteCountToString(voteCount: Int): String {
        if (voteCount < 1000)
            return voteCount.toString()

        val voteCountText: Int?

        val divide = (voteCount / 1000).toFloat()

        val mod = voteCount % 1000

        if (mod > 100) {
            voteCountText = (mod / 100)
        } else {
            return "${divide.toInt()} k"
        }

        return "${divide.toInt()}.$voteCountText k"
    }
}