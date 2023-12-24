package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dataSource.movie

import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.remote.dto.movie.MovieDto
import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.api.DetailApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.video.VideosDto
import javax.inject.Inject

class MovieDetailRemoteDataSourceImpl @Inject constructor(
    private val detailApi: DetailApi
) : MovieDetailRemoteDataSource {
    override suspend fun getMovieDetail(movieId: Int, language: String): MovieDetailDto {
        return tryApiCall {
            detailApi.getMovieDetail(movieId, language)
        }
    }

    override suspend fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
        page: Int
    ): ApiResponse<MovieDto> {
        return tryApiCall {
            detailApi.getRecommendationsForMovie(movieId, language, page)
        }
    }

    override suspend fun getMovieVideos(movieId: Int, language: String): VideosDto {
        return tryApiCall {
            detailApi.getMovieVideos(movieId, language)
        }
    }
}