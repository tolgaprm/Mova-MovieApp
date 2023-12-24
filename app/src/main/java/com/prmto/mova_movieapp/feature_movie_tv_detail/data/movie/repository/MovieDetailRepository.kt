package com.prmto.mova_movieapp.feature_movie_tv_detail.data.movie.repository

import com.prmto.mova_movieapp.core.data.mapper.toMovie
import com.prmto.mova_movieapp.core.data.safeApiCallReturnResource
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toMovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toVideo
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.movie.datasources.MovieDetailRemoteDataSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.model.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieDetailRemoteDataSource: MovieDetailRemoteDataSource,
) : MovieDetailRepository {
    override suspend fun getMovieDetail(
        movieId: Int,
        language: String,
        countryIsoCode: String
    ): Resource<MovieDetail> {
        return safeApiCallReturnResource {
            movieDetailRemoteDataSource.getMovieDetail(movieId, language)
                .toMovieDetail(countryIsoCode = countryIsoCode)
        }
    }

    override fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
        page: Int
    ): Flow<List<Movie>> {
        return flow {
            val movie =
                movieDetailRemoteDataSource.getRecommendationsForMovie(movieId, language, page)
            emit(movie.results.map { it.toMovie() })
        }
    }

    override suspend fun getMovieVideos(movieId: Int, language: String): Resource<Videos> {
        return safeApiCallReturnResource {
            movieDetailRemoteDataSource.getMovieVideos(movieId, language).toVideo()
        }
    }
}