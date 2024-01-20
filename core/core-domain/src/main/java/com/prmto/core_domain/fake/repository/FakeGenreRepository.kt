package com.prmto.core_domain.fake.repository

import com.prmto.core_domain.models.genre.Genre
import com.prmto.core_domain.models.genre.GenreList
import com.prmto.core_domain.repository.GenreRepository

class FakeGenreRepository : GenreRepository {
    val genreListEn = listOf(
        Genre(28, "Action"),
        Genre(12, "Adventure"),
        Genre(16, "Animation"),
        Genre(35, "Comedy"),
    )

    val genreListTr = listOf(
        Genre(28, "Aksiyon"),
        Genre(12, "Macera"),
        Genre(16, "Animasyon"),
        Genre(35, "Komedi")
    )

    override suspend fun getMovieGenreList(language: String): GenreList {
        return if (language == "en") {
            GenreList(genreListEn)
        } else {
            GenreList(genreListTr)
        }
    }

    override suspend fun getTvGenreList(language: String): GenreList {
        return if (language == "en") {
            GenreList(genreListEn)
        } else {
            GenreList(genreListTr)
        }
    }
}