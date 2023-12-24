package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dataSource.movie

import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.remote.dto.movie.MovieDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.video.VideosDto

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