package com.prmto.upcoming_data.remote.datasource

import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.util.tryApiCall
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.upcoming_data.remote.api.UpcomingApi
import com.prmto.upcoming_data.remote.dto.UpComingApiResponse
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpcomingMovieRemoteDataSourceImpl @Inject constructor(
    private val upComingApi: UpcomingApi,
    private val dispatcherProvider: DispatcherProvider
) : UpcomingMovieRemoteDataSource {
    override suspend fun getUpComingMovies(
        page: Int,
        language: String
    ): UpComingApiResponse<MovieDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                upComingApi.getUpComingMovies(
                    page = page,
                    language = language
                )
            }
        }
    }
}