package com.prmto.mova_movieapp.domain.repository

import com.prmto.mova_movieapp.domain.models.Genre

interface RemoteRepository {

    suspend fun getMovieGenreList(
        language: String
    ): List<Genre>

    suspend fun getTvGenreList(
        language: String
    ): List<Genre>
}