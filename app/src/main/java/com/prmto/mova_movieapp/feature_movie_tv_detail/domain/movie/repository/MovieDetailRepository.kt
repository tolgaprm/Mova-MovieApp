package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.repository

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun getMovieDetail(
        movieId: Int,
        language: String,
        countryIsoCode: String
    ): Resource<MovieDetail>

    fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
        page: Int = 1
    ): Flow<List<Movie>>

    suspend fun getMovieVideos(
        movieId: Int,
        language: String
    ): Resource<Videos>
}