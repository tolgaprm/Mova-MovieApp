package com.prmto.mova_movieapp.feature_movie_tv_detail.data.repository.movie

import com.prmto.mova_movieapp.core.data.remote.mapper.movie.toMovie
import com.prmto.mova_movieapp.core.data.util.safeApiCallReturnResource
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dataSource.movie.MovieDetailRemoteDataSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.detail.movie.toMovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.video.toVideo
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.movie.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.movie.MovieDetailRepository
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