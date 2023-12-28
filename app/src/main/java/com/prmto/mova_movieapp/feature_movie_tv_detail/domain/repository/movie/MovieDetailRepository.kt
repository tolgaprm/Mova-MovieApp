package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.movie

import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.movie.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
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