package com.prmto.mova_movieapp.feature_explore.domain.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_explore.data.dto.SearchDto
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {

    fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>>


    fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>>

    fun multiSearch(
        query: String,
        language: String
    ): Flow<PagingData<SearchDto>>

}