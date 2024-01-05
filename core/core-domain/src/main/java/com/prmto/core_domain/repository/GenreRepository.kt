package com.prmto.core_domain.repository

import com.prmto.core_domain.models.genre.GenreList

interface GenreRepository {
    suspend fun getMovieGenreList(
        language: String
    ): GenreList

    suspend fun getTvGenreList(
        language: String
    ): GenreList
}