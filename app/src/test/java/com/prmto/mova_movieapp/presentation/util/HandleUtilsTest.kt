package com.prmto.mova_movieapp.presentation.util

import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.data.models.Genre
import com.prmto.mova_movieapp.domain.models.Movie
import org.junit.Test

class HandleUtilsTest {


    private val movieGenreList = listOf<Genre>(
        Genre(id = 1, "Action"),
        Genre(id = 2, "Drama"),
        Genre(id = 3, "Animation"),
        Genre(id = 4, "Adventure"),
        Genre(id = 5, "Comedy"),
        Genre(id = 6, "Crime"),
        Genre(id = 7, "Family"),
        Genre(id = 8, "Horror"),
    )

    @Test
    fun `take releaseData like 2016-08-03 return just year`() {
        val releaseDate = "2016-08-03"
        assertThat(
            HandleUtils.handleReleaseDate(releaseDate)
        ).matches("2016")
    }

    @Test
    fun `the types of the given movie object are written by separating them with commas if they exist in the movieGenreList`() {
        val movie = Movie(
            id = 1,
            overview = "",
            title = "",
            originalTitle = "",
            posterPath = "",
            backdropPath = "",
            releaseDate = "",
            genreIds = listOf(
                2, 3, 6
            ),
            voteAverage = 0.0,
            voteCount = 1
        )

        assertThat(
            HandleUtils.handleGenreText(
                movieGenreList = movieGenreList,
                movie = movie
            )
        ).matches("Drama, Animation, Crime")

    }

    @Test
    fun `If the movie genreList is empty, return empty string`(){
        val movie = Movie(
            id = 1,
            overview = "",
            title = "",
            originalTitle = "",
            posterPath = "",
            backdropPath = "",
            releaseDate = "",
            genreIds = listOf(),
            voteAverage = 0.0,
            voteCount = 1
        )

        assertThat(
            HandleUtils.handleGenreText(
                movieGenreList = movieGenreList,
                movie = movie
            )
        ).isEmpty()
    }

    @Test
    fun `the types of the given movie object are written empty string if they not exist in the movieGenreList`() {
        val movie = Movie(
            id = 1,
            overview = "",
            title = "",
            originalTitle = "",
            posterPath = "",
            backdropPath = "",
            releaseDate = "",
            genreIds = listOf(
                11
            ),
            voteAverage = 0.0,
            voteCount = 1
        )

        assertThat(
            HandleUtils.handleGenreText(
                movieGenreList = movieGenreList,
                movie = movie
            )
        ).isEmpty()

    }

    @Test
    fun `the types of the given list of genre ids return the first type matched in the movieGenreList`() {
        val genreIds = listOf(
            3, 6, 8
        )

        assertThat(
            HandleUtils.handleGenreOneText(
                movieGenreList = movieGenreList,
                genreIds = genreIds
            )
        ).matches("Animation")

    }

    @Test
    fun `return the same value if the vote value given is less than 1000`() {
        val voteCount = 600
        assertThat(
            HandleUtils.handleVoteCount(voteCount)
        ).matches(voteCount.toString())
    }

    @Test
    fun `If the vote value is greater than 1000 and the value is a multiple of 1000, its value is expressed as k`() {
        val voteCount = 2000
        assertThat(
            HandleUtils.handleVoteCount(voteCount)
        ).matches("2 k")
    }

    @Test
    fun `If the vote value is greater than 1000, its value is expressed as k`() {
        val voteCount = 2300
        assertThat(
            HandleUtils.handleVoteCount(voteCount)
        ).matches("2.3 k")
    }
}