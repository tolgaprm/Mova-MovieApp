package com.prmto.data.remote.dataSource.movie

import com.prmto.core_data.dispatcher.DispatcherProvider
import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.util.tryApiCall
import com.prmto.data.remote.api.DetailApi
import com.prmto.data.remote.dto.detail.movie.MovieDetailDto
import com.prmto.data.remote.dto.detail.video.VideosDto
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailRemoteDataSourceImpl @Inject constructor(
    private val detailApi: DetailApi,
    private val dispatcherProvider: DispatcherProvider
) : MovieDetailRemoteDataSource {
    override suspend fun getMovieDetail(movieId: Int, language: String): MovieDetailDto {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                detailApi.getMovieDetail(movieId, language)
            }
        }
    }

    override suspend fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
        page: Int
    ): ApiResponse<MovieDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                detailApi.getRecommendationsForMovie(movieId, language, page)
            }
        }
    }

    override suspend fun getMovieVideos(movieId: Int, language: String): VideosDto {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                detailApi.getMovieVideos(movieId, language)
            }
        }
    }
}