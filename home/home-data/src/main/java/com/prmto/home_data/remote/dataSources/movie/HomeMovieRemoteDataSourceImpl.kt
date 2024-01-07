package com.prmto.home_data.remote.dataSources.movie

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.util.tryApiCall
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.home_data.remote.api.HomeApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeMovieRemoteDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val dispatcherProvider: DispatcherProvider
) : HomeMovieRemoteDataSource {
    override suspend fun getNowPlayingMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                homeApi.getNowPlayingMovies(
                    language = language,
                    page = page,
                    region = region
                )
            }
        }
    }

    override suspend fun getPopularMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                homeApi.getPopularMovies(
                    language = language,
                    page = page,
                    region = region
                )
            }
        }
    }

    override suspend fun getTopRatedMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                homeApi.getTopRatedMovies(
                    language = language,
                    page = page,
                    region = region
                )
            }
        }
    }
}