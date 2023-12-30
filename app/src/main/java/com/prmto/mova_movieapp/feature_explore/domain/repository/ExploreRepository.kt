package com.prmto.mova_movieapp.feature_explore.domain.repository

import androidx.paging.PagingData
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.mova_movieapp.feature_explore.domain.model.MultiSearch
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
    ): Flow<PagingData<MultiSearch>>
}