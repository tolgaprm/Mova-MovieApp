package com.prmto.mova_movieapp.feature_movie_tv_detail.data.movie.datasources

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.video.VideosDto

interface MovieDetailRemoteDataSource {

    suspend fun getMovieDetail(
        movieId: Int,
        language: String
    ): MovieDetailDto

    suspend fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
        page: Int = 1
    ): ApiResponse<MovieDto>

    suspend fun getMovieVideos(
        movieId: Int,
        language: String
    ): VideosDto
}