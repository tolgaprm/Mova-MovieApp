package com.prmto.mova_movieapp.feature_upcoming.domain.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.util.Constants
import kotlinx.coroutines.flow.Flow

interface UpComingRepository {
    fun getUpComingMovies(
        page: Int = Constants.STARTING_PAGE,
        language: String
    ): Flow<PagingData<Movie>>
}