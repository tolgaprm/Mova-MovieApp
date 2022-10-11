package com.prmto.mova_movieapp.domain.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.domain.models.GenreList
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.util.Constants
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getMovieGenreList(
        language: String
    ): GenreList

    suspend fun getTvGenreList(
        language: String
    ): GenreList

    fun getNowPlayingMovies(
        language: String = Constants.DEFAULT_LANGUAGE,
        region: String = Constants.DEFAULT_REGION
    ): Flow<PagingData<Movie>>
}