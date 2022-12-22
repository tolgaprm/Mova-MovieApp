package com.prmto.mova_movieapp.feature_home.data.dto

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MovieDtoTest {

    private lateinit var listMovieDto: List<MovieDto>

    @Before
    fun setup() {
        listMovieDto = listOf(
            MovieDto(
                id = 238,
                adult = false,
                backdropPath = "backdropPath.jpg",
                genreIds = listOf(18, 80),
                originalLanguage = "en",
                originalTitle = "The Godfather",
                overview = "Spanning the years 1945 to 1955",
                popularity = 95.112,
                posterPath = "posterPath.jpg",
                releaseDate = "1972-03-14",
                title = "The Godfather",
                video = false,
                voteAverage = 8.7,
                voteCount = 17034
            )
        )
    }

    @Test
    fun `list of the moviesDto can be mapped to list of the movie`() {
        val movieEntity = listMovieDto.toMovieList().first()

        assertThat(movieEntity.id).isEqualTo(238)
        assertThat(movieEntity.overview).isEqualTo("Spanning the years 1945 to 1955")
        assertThat(movieEntity.title).isEqualTo("The Godfather")
        assertThat(movieEntity.originalTitle).isEqualTo("The Godfather")
        assertThat(movieEntity.posterPath).isEqualTo("posterPath.jpg")
        assertThat(movieEntity.releaseDate).isEqualTo("1972-03-14")
        assertThat(movieEntity.genreIds).isEqualTo(listOf(18, 80))
        assertThat(movieEntity.voteCount).isEqualTo(17034)
        assertThat(movieEntity.voteAverage).isEqualTo(8.7)
    }
}