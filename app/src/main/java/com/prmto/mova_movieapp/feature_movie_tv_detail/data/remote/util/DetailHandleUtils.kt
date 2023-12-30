package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.util

import com.prmto.core_data.util.HandleUtils
import com.prmto.mova_movieapp.feature_movie_tv_detail.util.Constants

object DetailHandleUtils {

    fun calculateRatingBarValue(voteAverage: Double?): Float {
        if (voteAverage == null) return 0f
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
        firstAirDate: String?,
        lastAirDate: String?,
        status: String?
    ): String {
        if (firstAirDate == null || lastAirDate == null || status == null) return ""
        val firstAirDateValue = HandleUtils.convertToYearFromDate(firstAirDate)
        return if (status == Constants.TV_SERIES_STATUS_ENDED) {
            val lastAirDateValue = HandleUtils.convertToYearFromDate(lastAirDate)
            "${firstAirDateValue}-${lastAirDateValue}"
        } else {
            "$firstAirDateValue-"
        }
    }
}