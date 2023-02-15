package com.prmto.mova_movieapp.core.domain.repository

import com.prmto.mova_movieapp.core.data.dto.GenreList

interface RemoteRepository {
    suspend fun getMovieGenreList(
        language: String
    ): GenreList

    suspend fun getTvGenreList(
        language: String
    ): GenreList
}