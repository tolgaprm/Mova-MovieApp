package com.prmto.mova_movieapp.feature_movie_tv_detail.data.movie.datasources

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.tryApiCall
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.api.DetailApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.video.VideosDto
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