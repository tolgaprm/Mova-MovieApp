package com.prmto.mova_movieapp.feature_home.data.tv.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.data.paging.getPagingTvSeries
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_home.data.tv.datasource.HomeTvRemoteDataSource
import com.prmto.mova_movieapp.feature_home.domain.tv.HomeTvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeTvRepositoryImpl @Inject constructor(
    private val homeTvRemoteDataSource: HomeTvRemoteDataSource
) : HomeTvRepository {
    override fun getPopularTvs(language: String): Flow<PagingData<TvSeries>> {
        return getPagingTvSeries { page ->
            homeTvRemoteDataSource.getPopularTvs(
                page = page,
                language = language
            )
        }
    }

    override fun getTopRatedTvs(language: String): Flow<PagingData<TvSeries>> {
        return getPagingTvSeries { page ->
            homeTvRemoteDataSource.getTopRatedTvs(
                page = page,
                language = language
            )
        }
    }
}