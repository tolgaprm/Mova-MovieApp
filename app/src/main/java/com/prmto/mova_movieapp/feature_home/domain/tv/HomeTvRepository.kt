package com.prmto.mova_movieapp.feature_home.domain.tv

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import kotlinx.coroutines.flow.Flow

interface HomeTvRepository {

    fun getPopularTvs(
        language: String
    ): Flow<PagingData<TvSeries>>

    fun getTopRatedTvs(
        language: String
    ): Flow<PagingData<TvSeries>>
}