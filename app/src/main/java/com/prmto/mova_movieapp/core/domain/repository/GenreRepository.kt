package com.prmto.mova_movieapp.core.domain.repository

import com.prmto.mova_movieapp.core.domain.models.genre.GenreList

interface GenreRepository {
    suspend fun getMovieGenreList(
        language: String
    ): GenreList

    suspend fun getTvGenreList(
        language: String
    ): GenreList
}