package com.prmto.mova_movieapp.feature_upcoming.domain.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie
import kotlinx.coroutines.flow.Flow

interface UpComingRepository {
    fun getUpComingMovies(
        page: Int = Constants.STARTING_PAGE,
        language: String
    ): Flow<PagingData<UpcomingMovie>>


}