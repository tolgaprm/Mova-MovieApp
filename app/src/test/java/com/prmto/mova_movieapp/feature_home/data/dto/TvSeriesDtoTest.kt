package com.prmto.mova_movieapp.feature_home.data.dto

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class TvSeriesDtoTest {

    private lateinit var listTvSeriesDto: List<TvSeriesDto>

    @Before
    fun setup() {
        listTvSeriesDto = listOf(
            TvSeriesDto(
                id = 130392,
                overview = "From relative obscurity and a seemingly normal life",
                name = "The D'Amelio Show",
                originalName = "The D'Amelio Show",
                posterPath = "/z0iCS5Znx7TeRwlYSd4c01Z0lFx.jpg",
                backdropPath = "/7q448EVOnuE3gVAx24krzO7SNXM.jpg",
                firstAirDate = "2021-09-03",
                genreIds = listOf(
                    10764
                ),
                originCountry = listOf(
                    "US"
                ),
                originalLanguage = "en",
                popularity = 30.557,
                voteAverage = 9.0,
                voteCount = 3147
            )
        )
    }

    @Test
    fun `list of the tvSeriesDto can be mapped to list of the tvSeries`() {
        val tvSeriesEntity = listTvSeriesDto.toTvSeries().first()

        assertThat(tvSeriesEntity.id).isEqualTo(130392)
        assertThat(tvSeriesEntity.overview).isEqualTo("From relative obscurity and a seemingly normal life")
        assertThat(tvSeriesEntity.name).isEqualTo("The D'Amelio Show")
        assertThat(tvSeriesEntity.originalName).isEqualTo("The D'Amelio Show")
        assertThat(tvSeriesEntity.posterPath).isEqualTo("/z0iCS5Znx7TeRwlYSd4c01Z0lFx.jpg")
        assertThat(tvSeriesEntity.backdropPath).isEqualTo("/7q448EVOnuE3gVAx24krzO7SNXM.jpg")
        assertThat(tvSeriesEntity.firstAirDate).isEqualTo("2021-09-03")
        assertThat(tvSeriesEntity.genreIds).isEqualTo(listOf(10764))
        assertThat(tvSeriesEntity.voteCount).isEqualTo(3147)
        assertThat(tvSeriesEntity.voteAverage).isEqualTo(9.0)
    }
}