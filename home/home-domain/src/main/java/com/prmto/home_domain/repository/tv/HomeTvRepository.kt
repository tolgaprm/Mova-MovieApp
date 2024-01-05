package com.prmto.home_domain.repository.tv

import androidx.paging.PagingData
import com.prmto.core_domain.models.tv.TvSeries
import kotlinx.coroutines.flow.Flow

interface HomeTvRepository {

    fun getPopularTvs(
        language: String
    ): Flow<PagingData<TvSeries>>

    fun getTopRatedTvs(
        language: String
    ): Flow<PagingData<TvSeries>>
}