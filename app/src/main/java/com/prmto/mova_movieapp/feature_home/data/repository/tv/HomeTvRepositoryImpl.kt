package com.prmto.mova_movieapp.feature_home.data.repository.tv

import androidx.paging.PagingData
import com.prmto.core_data.util.paging.getPagingTvSeries
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.mova_movieapp.feature_home.data.remote.dataSources.tv.HomeTvRemoteDataSource
import com.prmto.mova_movieapp.feature_home.domain.repository.tv.HomeTvRepository
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