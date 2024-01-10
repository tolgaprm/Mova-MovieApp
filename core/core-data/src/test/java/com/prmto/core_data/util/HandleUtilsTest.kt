package com.prmto.core_data.util

import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.models.genre.Genre
import org.junit.Test

class HandleUtilsTest {

    @Test
    fun `convertToYearFromDate should return empty string when releaseDate is null`() {
        val releaseDate = null
        assertThat(HandleUtils.convertToYearFromDate(releaseDate)).isEmpty()
    }

    @Test
    fun `convertToYearFromDate should return year when releaseDate is not null`() {
        val releaseDate = "2021-01-01"
        assertThat(HandleUtils.convertToYearFromDate(releaseDate)).isEqualTo("2021")
    }

    @Test
    fun `getGenresBySeparatedByComma should return empty string when genreList is null`() {
        val genreList = null
        assertThat(HandleUtils.getGenresBySeparatedByComma(genreList)).isEmpty()
    }

    @Test
    fun `getGenresBySeparatedByComma should return genres separated by comma when genreList is not null`() {
        val genreList = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Comedy")
        )
        val expected = "Action, Adventure, Comedy"
        assertThat(HandleUtils.getGenresBySeparatedByComma(genreList)).isEqualTo(expected)
    }

    @Test
    fun `formatVoteCount should return empty string when voteCount is null`() {
        val voteCount = null
        assertThat(HandleUtils.formatVoteCount(voteCount)).isEmpty()
    }

    @Test
    fun `formatVoteCount should return voteCount when voteCount is less than 1000`() {
        val voteCount = 999
        assertThat(HandleUtils.formatVoteCount(voteCount)).isEqualTo("999")
    }

    @Test
    fun `formatVoteCount should return voteCount in thousand when voteCount is greater than 1000`() {
        val voteCount = 1000
        assertThat(HandleUtils.formatVoteCount(voteCount)).isEqualTo("1k")
    }
}