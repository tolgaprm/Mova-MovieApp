package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getMovieDetail(
        language: String,
        movieId: Int,
    ): MovieDetail

    suspend fun getTvDetail(
        language: String,
        tvId: Int,
    ): TvDetail

    suspend fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
    ): Flow<List<Movie>>

    suspend fun getRecommendationsForTv(
        tvId: Int,
        language: String,
    ): Flow<List<TvSeries>>

    suspend fun getMovieVideos(
        movieId: Int,
        language: String,
    ): Videos

    suspend fun getTvVideos(
        tvId: Int,
        language: String,
    ): Videos
}