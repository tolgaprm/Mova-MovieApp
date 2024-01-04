package com.prmto.upcoming_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.core_data.util.Constants
import com.prmto.database.MovaDatabase
import com.prmto.database.dao.movie.UpComingDao
import com.prmto.database.entity.movie.UpcomingRemindEntity
import com.prmto.upcoming_data.remote.datasource.UpcomingMovieRemoteDataSource
import com.prmto.upcoming_data.remote.paging_source.UpcomingMoviePagingSource
import com.prmto.upcoming_domain.model.UpcomingMovie
import com.prmto.upcoming_domain.repository.UpcomingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpcomingRepositoryImpl @Inject constructor(
    private val upcomingMovieRemoteDataSource: UpcomingMovieRemoteDataSource,
    movaDatabase: MovaDatabase
) : UpcomingRepository {

    private val upcomingDao: UpComingDao = movaDatabase.upcomingDao

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

    override suspend fun insertUpcomingRemind(upcomingMovie: UpcomingMovie) {
        upcomingDao.insertUpcomingRemind(upcomingMovie.toUpcomingRemindEntity())
    }

    override suspend fun deleteUpcomingRemind(upcomingMovie: UpcomingMovie) {
        upcomingDao.deleteUpcomingRemind(upcomingMovie.toUpcomingRemindEntity())
    }

    override fun getAllUpcomingRemind(): Flow<List<UpcomingRemindEntity>> {
        return upcomingDao.getUpcomingRemindList()
    }

    private fun UpcomingMovie.toUpcomingRemindEntity(): UpcomingRemindEntity {
        return UpcomingRemindEntity(
            movieId = movie.id,
            movieTitle = movie.title,
            movieReleaseDate = movie.fullReleaseDate ?: ""
        )
    }
}