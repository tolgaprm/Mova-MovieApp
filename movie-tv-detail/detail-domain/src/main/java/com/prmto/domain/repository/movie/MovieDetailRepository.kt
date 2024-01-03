package com.prmto.domain.repository.movie

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.util.Resource
import com.prmto.domain.models.detail.movie.MovieDetail
import com.prmto.domain.models.detail.video.Videos
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