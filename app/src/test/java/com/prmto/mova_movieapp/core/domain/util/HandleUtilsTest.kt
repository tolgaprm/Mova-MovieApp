package com.prmto.mova_movieapp.core.domain.util

import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.util.HandleUtils.calculateRatingBarValue
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.util.HandleUtils.convertRuntimeAsHourAndMinutes
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.util.HandleUtils.convertTvSeriesReleaseDateBetweenFirstAndLastDate
import org.junit.Test

class HandleUtilsTest {


    @Test
    fun convertRuntimeAsHourAndMinutes() {
        val runtime = 165 // 2 hours in 35 minutes

        val result = convertRuntimeAsHourAndMinutes(runtime)

        assertThat(result[Constants.HOUR_KEY]).isEqualTo("2")
        assertThat(result[Constants.MINUTES_KEY]).isEqualTo("45")
    }

    @Test
    fun `ratingValue,calculateRatingBarValue`() {
        val voteAverage = 7.5

        val result = calculateRatingBarValue(voteAverage)

        assertThat(result).isEqualTo(3.75f)

    }

    @Test
    fun `releaseDate,convertTvSeriesReleaseDateBetweenFirstAndLastDate`() {
        val firstAirDate = "2010-01-01"
        val lastAirDate = "2015-01-01"
        val statusEnded = Constants.TV_SERIES_STATUS_ENDED
        val statusContinuing = "continue"


        val resultEnded = convertTvSeriesReleaseDateBetweenFirstAndLastDate(
            firstAirDate,
            lastAirDate,
            statusEnded
        )
        val resultContinuing = convertTvSeriesReleaseDateBetweenFirstAndLastDate(
            firstAirDate,
            lastAirDate,
            statusContinuing
        )

        assertThat(resultEnded).isEqualTo("2010-2015")
        assertThat(resultContinuing).isEqualTo("2010-")
    }


}