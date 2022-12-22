package com.prmto.mova_movieapp.core.repository

import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.core.data.dto.GenreList
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository

class FakeRemoteRepository : RemoteRepository {

    val movieGenreListLanguageTr = GenreList(
        genres = listOf(
            Genre(id = 1, "Aksiyon"),
            Genre(id = 2, "Macera"),
            Genre(id = 3, "Animasyon"),
            Genre(id = 4, "Komedi"),
        )

    )

    val movieGenreListLanguageEn = GenreList(
        listOf(
            Genre(id = 1, "Action"),
            Genre(id = 2, "Adventure"),
            Genre(id = 3, "Animation"),
            Genre(id = 4, "Comedy"),
        )
    )

    val tvGenreListLanguageTr = GenreList(
        genres = listOf(
            Genre(id = 1, "Aksiyon & Macera"),
            Genre(id = 2, "Suç"),
            Genre(id = 3, "Belgesel"),
            Genre(id = 4, "Aile"),
        )

    )

    val tvGenreListLanguageEn = GenreList(
        listOf(
            Genre(id = 1, "Action & Adventure"),
            Genre(id = 2, "Crime"),
            Genre(id = 3, "Documentry"),
            Genre(id = 4, "Family"),
        )
    )


    override suspend fun getMovieGenreList(language: String): GenreList {
        return if (language.lowercase() == "tr") {
            movieGenreListLanguageTr
        } else {
            movieGenreListLanguageEn
        }
    }

    override suspend fun getTvGenreList(language: String): GenreList {
        return if (language.lowercase() == "tr") {
            tvGenreListLanguageTr
        } else {
            tvGenreListLanguageEn
        }
    }
}