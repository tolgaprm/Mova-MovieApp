package com.prmto.mova_movieapp.feature_movie_tv_detail.data.repository

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.data_source.remote.DetailApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailApi: DetailApi
) : DetailRepository {

    override suspend fun getMovieDetail(language: String, movieId: Int): MovieDetailDto {
        return detailApi.getMovieDetail(
            language = language,
            movieId = movieId
        )
    }

    override suspend fun getTvDetail(language: String, tvId: Int): TvDetailDto {
        return detailApi.getTvDetail(
            language = language,
            tvId = tvId
        )
    }
}