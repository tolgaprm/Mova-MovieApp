package com.prmto.mova_movieapp.feature_home.data.movie.datasource

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.tryApiCall
import com.prmto.mova_movieapp.feature_home.data.api.HomeApi
import javax.inject.Inject

class HomeMovieRemoteDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeMovieRemoteDataSource {
    override suspend fun getNowPlayingMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieDto> {
        return tryApiCall {
            homeApi.getNowPlayingMovies(
                language = language,
                page = page,
                region = region
            )
        }
    }

    override suspend fun getPopularMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieDto> {
        return tryApiCall {
            homeApi.getPopularMovies(
                language = language,
                page = page,
                region = region
            )
        }
    }

    override suspend fun getTopRatedMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieDto> {
        return tryApiCall {
            homeApi.getTopRatedMovies(
                language = language,
                page = page,
                region = region
            )
        }
    }
}