package com.prmto.mova_movieapp.feature_movie_tv_detail.data.repository

import com.prmto.mova_movieapp.core.data.mapper.toMovie
import com.prmto.mova_movieapp.core.data.mapper.toTvSeries
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toMovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toTvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toVideo
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.DetailApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailApi: DetailApi,
) : DetailRepository {

    override suspend fun getMovieDetail(language: String, movieId: Int): MovieDetail {
        return detailApi.getMovieDetail(
            language = language,
            movieId = movieId
        ).toMovieDetail()
    }

    override suspend fun getTvDetail(language: String, tvId: Int): TvDetail {
        return detailApi.getTvDetail(
            language = language,
            tvId = tvId
        ).toTvDetail()
    }

    override suspend fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
    ): Flow<List<Movie>> {
        return flow {
            val movies = detailApi.getRecommendationsForMovie(
                movieId = movieId,
                language = language
            ).results.map { it.toMovie() }
            emit(movies)
        }

    }

    override suspend fun getRecommendationsForTv(
        tvId: Int,
        language: String
    ): Flow<List<TvSeries>> {
        return flow {
            val tvSeries = detailApi.getRecommendationsForTv(
                tvId = tvId,
                language = language
            ).results.map { it.toTvSeries() }
            emit(tvSeries)
        }
    }

    override suspend fun getMovieVideos(movieId: Int, language: String): Videos {
        return detailApi.getMovieVideos(
            movieId = movieId,
            language = language
        ).toVideo()
    }

    override suspend fun getTvVideos(tvId: Int, language: String): Videos {
        return detailApi.getTvVideos(
            tvId = tvId,
            language = language
        ).toVideo()
    }
}