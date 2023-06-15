package com.prmto.mova_movieapp.feature_upcoming.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_upcoming.data.remote.UpComingApi
import com.prmto.mova_movieapp.feature_upcoming.data.remote.paging_source.UpcomingMoviePagingSource
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpComingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpComingRepositoryImpl @Inject constructor(
    private val api: UpComingApi
) : UpComingRepository {

    override fun getUpComingMovies(
        page: Int,
        language: String
    ): Flow<PagingData<UpcomingMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UpcomingMoviePagingSource(
                    api = api,
                    language = language
                )
            }
        ).flow
    }

}