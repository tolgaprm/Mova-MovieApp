package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.util

import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.core.util.Constants

object HandleUtils {

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
        val firstAirDateValue = HandleUtils.convertToYearFromDate(firstAirDate)
        return if (status == Constants.TV_SERIES_STATUS_ENDED) {
            val lastAirDateValue = HandleUtils.convertToYearFromDate(lastAirDate)
            "${firstAirDateValue}-${lastAirDateValue}"
        } else {
            "$firstAirDateValue-"
        }
    }
}