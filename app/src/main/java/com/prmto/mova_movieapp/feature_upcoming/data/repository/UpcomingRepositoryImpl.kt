package com.prmto.mova_movieapp.feature_upcoming.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_upcoming.data.local.dao.UpComingDao
import com.prmto.mova_movieapp.feature_upcoming.data.remote.datasource.UpcomingMovieRemoteDataSource
import com.prmto.mova_movieapp.feature_upcoming.data.remote.paging_source.UpcomingMoviePagingSource
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpcomingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpcomingRepositoryImpl @Inject constructor(
    private val upcomingMovieRemoteDataSource: UpcomingMovieRemoteDataSource,
    private val upcomingDao: UpComingDao
) : UpcomingRepository {

    override fun getUpComingMovies(
        language: String
    ): Flow<PagingData<UpcomingMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UpcomingMoviePagingSource(
                    fetchUpcomingMovie = { page ->
                        upcomingMovieRemoteDataSource.getUpComingMovies(
                            page = page,
                            language = language
                        ).results
                    }
                )
            }
        ).flow
    }

    override suspend fun insertUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity) {
        upcomingDao.insertUpcomingRemind(upcomingRemindEntity)
    }

    override suspend fun deleteUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity) {
        upcomingDao.deleteUpcomingRemind(upcomingRemindEntity)
    }

    override fun getAllUpcomingRemind(): Flow<List<UpcomingRemindEntity>> {
        return upcomingDao.getUpcomingRemindList()
    }
}