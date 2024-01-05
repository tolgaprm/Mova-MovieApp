package com.prmto.explore_domain.repository

import androidx.paging.PagingData
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.explore_domain.model.FilterBottomState
import com.prmto.explore_domain.model.MultiSearch
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